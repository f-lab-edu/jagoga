package com.project.jagoga.room.application;

import static com.project.jagoga.user.domain.Role.OWNER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import com.project.jagoga.accommodation.application.AccommodationService;
import com.project.jagoga.accommodation.domain.Accommodation;
import com.project.jagoga.accommodation.domain.Category;
import com.project.jagoga.accommodation.domain.address.City;
import com.project.jagoga.accommodation.domain.address.State;
import com.project.jagoga.accommodation.infrastructure.address.JpaCategoryRepository;
import com.project.jagoga.accommodation.infrastructure.address.JpaCityRepository;
import com.project.jagoga.accommodation.infrastructure.address.JpaStateRepository;
import com.project.jagoga.accommodation.presentation.dto.AccommodationRequestDto;
import com.project.jagoga.common.factory.AccommodationFactory;
import com.project.jagoga.exception.accommodation.NotExistAccommodationException;
import com.project.jagoga.exception.roomtype.NotExistRoomTypeException;
import com.project.jagoga.exception.user.ForbiddenException;
import com.project.jagoga.room.domain.Room;
import com.project.jagoga.room.presentation.dto.RoomCreateRequestDto;
import com.project.jagoga.roomtype.application.RoomTypeService;
import com.project.jagoga.roomtype.domain.RoomType;
import com.project.jagoga.roomtype.presentation.dto.RoomTypeCreateRequestDto;
import com.project.jagoga.user.application.UserService;
import com.project.jagoga.user.domain.AuthUser;
import com.project.jagoga.user.domain.User;
import com.project.jagoga.user.presentation.dto.request.UserCreateRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class RoomServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    JpaCategoryRepository jpaCategoryRepository;

    @Autowired
    JpaStateRepository jpaStateRepository;

    @Autowired
    JpaCityRepository jpaCityRepository;

    @Autowired
    private AccommodationService accommodationService;

    @Autowired
    RoomTypeService roomTypeService;

    @Autowired
    RoomService roomService;

    String email;
    String name;
    String phone;
    String password;

    UserCreateRequestDto userCreateRequestDto;
    User user;
    AuthUser authUser;

    long accommodationId;
    long roomTypeId;

    String roomNumber;

    @BeforeEach
    public void setUp() {
        email = "test1223@test";
        name = "testname";
        password = "@Aabcdef";
        phone = "010-1234-1234";

        userCreateRequestDto = new UserCreateRequestDto(email, name, password, phone);
        user = userService.signUp(userCreateRequestDto);
        authUser = AuthUser.createInstance(user.getId(), user.getEmail(), OWNER);

        Category category = new Category(null, "강릉/경포");
        jpaCategoryRepository.save(category);
        State state = new State(null, "강원");
        jpaStateRepository.save(state);
        City city = new City(null, "강릉시", state, category.getId());
        jpaCityRepository.save(city);

        AccommodationRequestDto accommodation = AccommodationFactory.mockAccommodationRequestDto(city);
        Accommodation savedAccommodation = accommodationService.saveAccommodation(accommodation, authUser);
        accommodationId = savedAccommodation.getId();

        RoomTypeCreateRequestDto roomTypeCreateRequestDto = new RoomTypeCreateRequestDto(
            "베이직", "베이직 타입의 방입니다.", 50000);
        RoomType savedRoomType = roomTypeService.registerRoomType(accommodationId, roomTypeCreateRequestDto, authUser);
        roomTypeId = savedRoomType.getId();

        roomNumber = "101";
    }

    @DisplayName("정상 룸 등록시 id가 생성된다.")
    @Test
    void registerRoom() {
        // given
        RoomCreateRequestDto roomCreateRequestDto = new RoomCreateRequestDto(roomNumber);

        // when
        Room room = roomService.registerRoom(accommodationId, roomTypeId, roomCreateRequestDto, authUser);

        assertThat(room.getId())
            .isEqualTo(roomService.getRoomById(room.getId()).getId());
    }

    @DisplayName("본인이 등록하지 않은 숙소에 room을 등록할 수 없다.")
    @Test
    void registerRoomTypeAtOtherAccommodation() {
        // given
        email = "test_other@test";
        userCreateRequestDto = new UserCreateRequestDto(email, name, password, phone);
        User otherUser = userService.signUp(userCreateRequestDto);
        authUser = AuthUser.createInstance(otherUser.getId(), otherUser.getEmail(), OWNER);

        RoomCreateRequestDto roomCreateRequestDto = new RoomCreateRequestDto(roomNumber);

        // when, then
        assertThrows(ForbiddenException.class,
            () -> roomService.registerRoom(accommodationId, roomTypeId, roomCreateRequestDto, authUser));
    }

    @DisplayName("존재하지 않는 숙소에 room을 등록할 수 없다.")
    @Test
    void registerRoomAtNotExistAccommodation() {
        // given
        RoomCreateRequestDto roomCreateRequestDto = new RoomCreateRequestDto(roomNumber);

        // when, then
        long notExistAccommodationId = 1111L;
        assertThrows(NotExistAccommodationException.class,
            () -> accommodationService.getAccommodationById(notExistAccommodationId));
        assertThrows(NotExistAccommodationException.class,
            () -> roomService.registerRoom(notExistAccommodationId, roomTypeId, roomCreateRequestDto, authUser));
    }

    @DisplayName("존재하지 않는 room type에 room을 등록할 수 없다.")
    @Test
    void registerRoomAtNotExistRoomType() {
        // given
        RoomCreateRequestDto roomCreateRequestDto = new RoomCreateRequestDto(roomNumber);

        // when, then
        long notExistRoomTypeId = 1111L;
        assertThrows(NotExistRoomTypeException.class,
            () -> roomTypeService.getRoomTypeById(notExistRoomTypeId));
        assertThrows(NotExistRoomTypeException.class,
            () -> roomService.registerRoom(accommodationId, notExistRoomTypeId, roomCreateRequestDto, authUser));
    }
}