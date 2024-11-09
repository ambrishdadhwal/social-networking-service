package com.social.network.restcontroller;

import com.social.network.presentation.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/settings")
@RequiredArgsConstructor
public class UserSettingsController {

    @GetMapping(value = "/default", consumes = "application/json", produces = "application/json")
    public CommonResponse<?> allSettings()
    {
        return null;
    }

    @GetMapping(value = "/privacy", consumes = "application/json", produces = "application/json")
    public CommonResponse<?> privacySetting()
    {
        return null;
    }

    @GetMapping(value = "/security", consumes = "application/json", produces = "application/json")
    public CommonResponse<?> securitySetting()
    {
        return null;
    }

    @DeleteMapping(value = "/delete-account", consumes = "application/json")
    public CommonResponse<?> deleteAccount()
    {
        return null;
    }

    @PutMapping(value = "/deactivate", consumes = "application/json")
    public CommonResponse<?> deactivateAccount()
    {
        return null;
    }

    @GetMapping(value = "/blocked-contacts", consumes = "application/json", produces = "application/json")
    public CommonResponse<?> blockedContacts()
    {
        return null;
    }
}
