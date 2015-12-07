package online.bbstats.service.currentuser;

import online.bbstats.domain.CurrentUser;

public interface CurrentUserService {

    boolean canAccessUser(CurrentUser currentUser, Long userId);

}
