package com.project.jagoga.roomtype.application;

import static com.project.jagoga.user.domain.Role.OWNER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import com.project.jagoga.accommodation.application.AccommodationService;
import com.project.jagoga.accommodation.domain.Accommodation;
import com.project.jagoga.category.domain.Category;
import com.project.jagoga.accommodation.domain.address.City;
import com.project.jagoga.accommodation.domain.address.State;
import com.project.jagoga.category.infrastructure.JpaCategoryRepository;
import com.project.jagoga.accommodation.infrastructure.address.JpaCityRepository;
import com.project.jagoga.accommodation.infrastructure.address.JpaStateRepository;
import com.project.jagoga.accommodation.presentation.dto.AccommodationRequestDto;
import com.project.jagoga.common.factory.AccommodationFactory;
import com.project.jagoga.exception.accommodation.NotExistAccommodationException;
import com.project.jagoga.exception.user.ForbiddenException;
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
class RoomTypeServiceTest {

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

    String email;
    String name;
    String phone;
    String password;

    UserCreateRequestDto userCreateRequestDto;
    User user;
    AuthUser authUser;

    long accommodationId;
    String roomTypeName;
    String description;
    int price;

    @BeforeEach
    public void setUp() {
        email = "test1223@test";
        name = "testname";
        password = "@Aabcdef";
        phone = "010-1234-1234";
        price = 25000;

        userCreateRequestDto = new UserCreateRequestDto(email, name, password, phone);
        user = userService.signUp(userCreateRequestDto);
        authUser = AuthUser.createInstance(user.getId(), user.getEmail(), OWNER);

        Category category = new Category(null, "??????/??????");
        jpaCategoryRepository.save(category);
        State state = new State(null, "??????");
        jpaStateRepository.save(state);
        City city = new City(null, "?????????", state, category.getId());
        jpaCityRepository.save(city);

        AccommodationRequestDto accommodation = AccommodationFactory.mockAccommodationRequestDto(city);
        Accommodation savedAccommodation = accommodationService.saveAccommodation(accommodation, authUser);

        accommodationId = savedAccommodation.getId();
        roomTypeName = "?????????";
        description = "????????? ????????? ????????????.";
    }

    @DisplayName("?????? ????????? ????????? id??? ????????????.")
    @Test
    void registerRoomtype() {
        // given
        RoomTypeCreateRequestDto roomTypeCreateRequestDto =
            new RoomTypeCreateRequestDto(roomTypeName, description, price);

        // when
        RoomType roomType = roomTypeService.registerRoomType(accommodationId, roomTypeCreateRequestDto, authUser);

        // then
        assertThat(roomType.getId())
            .isEqualTo(roomTypeService.getRoomTypeById(roomType.getId()).getId());
    }

    @DisplayName("????????? ???????????? ?????? ????????? roomType??? ????????? ??? ??????.")
    @Test
    void registerRoomTypeAtOtherAccommodation() {
        // given
        email = "test_other@test";
        userCreateRequestDto = new UserCreateRequestDto(email, name, password, phone);
        User otherUser = userService.signUp(userCreateRequestDto);
        authUser = AuthUser.createInstance(otherUser.getId(), otherUser.getEmail(), OWNER);

        RoomTypeCreateRequestDto roomTypeCreateRequestDto =
            new RoomTypeCreateRequestDto(roomTypeName, description, price);

        // when, then
        assertThrows(ForbiddenException.class,
            () -> roomTypeService.registerRoomType(accommodationId, roomTypeCreateRequestDto, authUser));
    }

    @DisplayName("???????????? ?????? ????????? roomType??? ????????? ??? ??????.")
    @Test
    void registerRoomTypeAtNotExistAccommodation() {
        // given
        RoomTypeCreateRequestDto roomTypeCreateRequestDto =
            new RoomTypeCreateRequestDto(roomTypeName, description, price);

        // when, then
        long notExistAccommodationId = 1111L;
        assertThrows(NotExistAccommodationException.class,
            () -> accommodationService.getAccommodationById(notExistAccommodationId));
        assertThrows(NotExistAccommodationException.class,
            () -> roomTypeService.registerRoomType(notExistAccommodationId, roomTypeCreateRequestDto, authUser));
    }
}