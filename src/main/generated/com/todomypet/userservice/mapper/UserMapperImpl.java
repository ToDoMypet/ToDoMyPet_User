package com.todomypet.userservice.mapper;

import com.todomypet.userservice.domain.node.User;
import com.todomypet.userservice.dto.GetUserDetailsDTO;
import com.todomypet.userservice.dto.MyPageResDTO;
import com.todomypet.userservice.dto.UserInfoResDTO;
import com.todomypet.userservice.dto.UserProfileResDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-24T00:17:17+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.8 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public MyPageResDTO userToMyPageResDTO(User user) {
        if ( user == null ) {
            return null;
        }

        MyPageResDTO.MyPageResDTOBuilder myPageResDTO = MyPageResDTO.builder();

        myPageResDTO.Protected( user.getProtected() );
        myPageResDTO.nickname( user.getNickname() );
        myPageResDTO.bio( user.getBio() );
        myPageResDTO.profilePicUrl( user.getProfilePicUrl() );
        myPageResDTO.personalCode( user.getPersonalCode() );

        return myPageResDTO.build();
    }

    @Override
    public GetUserDetailsDTO userToGetUserDetailsDTO(User user) {
        if ( user == null ) {
            return null;
        }

        GetUserDetailsDTO.GetUserDetailsDTOBuilder getUserDetailsDTO = GetUserDetailsDTO.builder();

        getUserDetailsDTO.id( user.getId() );

        return getUserDetailsDTO.build();
    }

    @Override
    public UserInfoResDTO userToUserInfoResDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserInfoResDTO.UserInfoResDTOBuilder userInfoResDTO = UserInfoResDTO.builder();

        userInfoResDTO.id( user.getId() );
        userInfoResDTO.nickname( user.getNickname() );
        userInfoResDTO.bio( user.getBio() );
        userInfoResDTO.profilePicUrl( user.getProfilePicUrl() );

        return userInfoResDTO.build();
    }

    @Override
    public UserProfileResDTO userToUserProfileResDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserProfileResDTO.UserProfileResDTOBuilder userProfileResDTO = UserProfileResDTO.builder();

        userProfileResDTO.id( user.getId() );
        userProfileResDTO.nickname( user.getNickname() );
        userProfileResDTO.profilePicUrl( user.getProfilePicUrl() );
        userProfileResDTO.bio( user.getBio() );
        if ( user.getCollectionCount() != null ) {
            userProfileResDTO.collectionCount( user.getCollectionCount() );
        }
        if ( user.getAchCount() != null ) {
            userProfileResDTO.achCount( user.getAchCount() );
        }
        if ( user.getFriendCount() != null ) {
            userProfileResDTO.friendCount( user.getFriendCount() );
        }

        return userProfileResDTO.build();
    }
}
