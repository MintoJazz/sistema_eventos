package util;

import java.util.Map;

public class Assets {
    public static final Map<String,String> ROLES = Map.of(
        "admin", "app_admin",
        "organizer", "app_organizer",
        "user", "app_attendee",
        "viewer", "app_viewer"
    );
}
