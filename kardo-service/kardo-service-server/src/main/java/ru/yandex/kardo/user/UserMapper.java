package ru.yandex.kardo.user;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.yandex.kardo.direction.Direction;
import ru.yandex.kardo.role.Role;
import ru.yandex.kardo.role.RoleMapper;
import ru.yandex.kardo.role.RoleName;
import ru.yandex.kardo.util.DateMapper;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserMapper {
    /*--------------------Основные методы--------------------*/
    public static User newUserRequestToUser(NewUserRequest userRequest) {
        return User.builder()
                .firstName(userRequest.getFirstName())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .registrationDate(LocalDateTime.now())
                .roles(Collections.singleton(Role.builder()
                        .id(1)
                        .name(RoleName.USER)
                        .build()))
                .build();
    }

    public static UserEmailResponse userToUserEmailResponse(User user) {
        UserEmailResponse userEmailResponse = new UserEmailResponse();
        userEmailResponse.setEmail(user.getEmail());

        return userEmailResponse;
    }

    public static UserProfileResponse userToUserProfileResponse(User user) {
        return UserProfileResponse.builder()
                .fullName(getFullName(user.getFirstName(), user.getLastName()))
                .countryAndCity(getCountryAndCity(user.getCountry(), user.getCity()))
                .directionAndStatus(getDirectionAndParticipation(user.getDirection(), user.getParticipation()))
                .build();
    }

    public static UserProfileSettingsResponse userToUserProfileSettingsResponse(User user) {
        return UserProfileSettingsResponse.builder()
                .fullName(getFullName(user.getFirstName(), user.getLastName()))
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .patronymic(user.getPatronymic())
                .birthday(DateMapper.getUserProfileBirthdayString(user.getBirthday()))
                .sex(user.getSex() != null ?
                        user.getSex().getDtoSex() : null)
                .email(user.getEmail())
                .phone(getUserPhone(user.getPhone()))
                .socialLink(user.getSocialLink())
                .country(user.getCountry())
                .region(user.getRegion())
                .city(user.getCity())
                .portfolioLink(user.getPortfolioLink())
                .aboutUser(user.getAboutUser())
                .direction(user.getDirection() != null ?
                        user.getDirection().getName().getDtoDirection() : null)
                .participation(user.getParticipation())
                .build();
    }

    public static UserCommunityShortDto userToUserShortCommunity(User user) {
        return UserCommunityShortDto.builder()
                .id(user.getId())
                .fullName(getFullName(user.getFirstName(), user.getLastName()))
                .countryAndCity(getCountryAndCity(user.getCountry(), user.getCity()))
                .directionAndStatus(getDirectionAndParticipation(user.getDirection(), user.getParticipation()))
                .build();
    }

    public static List<UserCommunityShortDto> userToUserShortCommunity(List<User> users) {
        return users.stream()
                .map(UserMapper::userToUserShortCommunity)
                .collect(Collectors.toList());
    }

    public static UserCommunityFullDto userToUserCommunityFullDto(User user) {
        return UserCommunityFullDto.builder()
                .id(user.getId())
                .fullName(getFullName(user.getFirstName(), user.getLastName()))
                .countryAndCity(getCountryAndCity(user.getCountry(), user.getCity()))
                .aboutUser(user.getAboutUser())
                .build();
    }

    public static GenerateUserTokenDto userToGenerateUserDto(User user) {
        return GenerateUserTokenDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .roles(RoleMapper.roleToRoleName(user.getRoles()))
                .build();
    }

    /*--------------------Вспомогательные методы--------------------*/
    private static String getCountryAndCity(String country, String city) {
        if (country == null && city == null) {
            return null;
        }
        if (country == null || city == null) {
            return country != null ? country : city;
        }

        return String.format("%s, %s", country, city);
    }

    private static String getFullName(String firstName, String lastName) {
        if (lastName == null) {
            return firstName;
        }

        return String.format("%s %s", firstName, lastName);
    }

    private static String getDirectionAndParticipation(Direction direction, Boolean participation) {
        if (direction == null) {
            return null;
        }
        if (participation == null || participation.equals(false)) {
            return direction.getName().getDtoDirection();
        }

        return String.format("%s [Участник]", direction.getName().getDtoDirection());
    }

    // возвращает телефон в формате: 8 800 555 35 35:
    private static String getUserPhone(String phone) {
        if (phone == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder(phone);
        builder.insert(phone.length() - 10, " ");
        builder.insert(phone.length() - 6, " ");
        builder.insert(phone.length() - 2, " ");
        builder.insert(phone.length() + 1, " ");

        return builder.toString();
    }
}