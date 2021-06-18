package com.fissher.wowtc.utils;

import com.fissher.wowtc.manager.StateManager;

public class RankDescriptionsProvider {
    public static String getCurrentTalentRankDescriptions(int id, int rank) {
        switch (StateManager.CURRENT_EXPANSION) {
            case 0: return RankDescriptionsVanilla.getCurrentTalentRankDescriptions(id, rank);
            case 1: return RankDescriptionsTbc.getCurrentTalentRankDescriptions(id, rank);
            case 2: return RankDescriptionsWotlk.getCurrentTalentRankDescriptions(id, rank);
            default: return "";
        }
    }
}
