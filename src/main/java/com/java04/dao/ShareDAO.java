package com.java04.dao;

import com.java04.dto.ShareSummaryDTO;
import com.java04.dto.SharedFriendDTO;
import com.java04.entity.Share;

import java.util.List;

public interface ShareDAO extends GenericDAO<Share, Long> {
    List<ShareSummaryDTO> getShareSummary();
    List<SharedFriendDTO> getSharedFriendsByVideoTitle(String title);
}
