package com.gerardrama.service.currentuser;

import com.gerardrama.entity.CurrentUser;

public interface CurrentUserService {
    boolean canAccessUser(CurrentUser currentUser, Long userId);
    boolean canAccessFlights(CurrentUser currentUser, Long userId, Integer tripId);
    boolean canChangeStatus(CurrentUser currentUser, Long userId, Integer tripId);
    boolean canSubmitStatus(CurrentUser currentUser, Long userId, Integer tripId);
    boolean canEditDeleteTrip(CurrentUser currentUser, Long userId, Integer tripId);
}
