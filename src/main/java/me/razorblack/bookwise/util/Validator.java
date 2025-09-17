package me.razorblack.bookwise.util;

import java.util.List;

import static me.razorblack.bookwise.constants.BaseConstants.*;

public class Validator {
    /**
     * Method to check if the given branchId is active
     * @param branchId Branch ID to check
     * @return true if branch is active, otherwise false
     */
    public static boolean isActiveBranch(String branchId) {
        List<String> activeBranched = List.of(ConfigFileReader.CONFIG.getPropertyValue(ACTIVE_BRANCHES, EMPTY_STRING).split(COMMA));
        return activeBranched.contains(branchId);
    }
}
