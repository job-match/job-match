package com.project.jobmatch.controllers.mvc.professional;

import com.project.jobmatch.models.JobAd;
import com.project.jobmatch.models.dto.JobAdDtoSearch;
import com.project.jobmatch.services.interfaces.JobAdService;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/professional-portal/job-ads")
public class JobAdMvcControllerForProfessionals {

    private final JobAdService jobAdService;

    public JobAdMvcControllerForProfessionals(JobAdService jobAdService) {
        this.jobAdService = jobAdService;
    }

    @GetMapping
    public String getAllJobAds(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "6") int size,
                               @RequestParam(defaultValue = "positionTitle") String sortField,
                               @RequestParam(defaultValue = "asc") String sortDirection,
                               @ModelAttribute("jobAdDtoSearch")JobAdDtoSearch jobAdDtoSearch,
                               Model model,
                               HttpSession httpSession) {
        //TODO Authenticate when ready

        if ("location".equals(sortField)) {
            sortField = "location.name";
        }

        Sort sort = Sort.by(sortDirection.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);

        PageRequest pageRequest = PageRequest.of(page, size, sort);

        Page<JobAd> jobAdPage = jobAdService.searchJobAdsPaginated(
                jobAdDtoSearch.getPositionTitle(),
                jobAdDtoSearch.getLocation(),
                jobAdDtoSearch.getMinSalary(),
                jobAdDtoSearch.getMaxSalary(),
                jobAdDtoSearch.getRequirement(),
                pageRequest);

        model.addAttribute("jobAdsPaged", jobAdPage.getContent());
        model.addAttribute("jobAdsAllSize", jobAdPage.getTotalElements());
        model.addAttribute("currentPage", jobAdPage.getNumber());
        model.addAttribute("totalPages", jobAdPage.getTotalPages());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);

        return "job-ad/job-ads-view";
    }
}
