package com.project.jobmatch.services.interfaces;

import com.project.jobmatch.models.Status;

public interface StatusService {

    Status getStatusByType(String type);
}
