package lecture5.web.controller;

import lecture5.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Pavel Karpukhin
 * @since 30.12.14
 */
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping("/notificationjob")
    public String performNotificationJob() {
        adminService.performNotificationJob();
        return "redirect:/jobs";
    }

    @RequestMapping("/rebuildindexjob.html")
    public ModelAndView performRebuildIndexJob() {
        adminService.performRebuildIndexJob();
        return new ModelAndView("redirect:/jobs");
    }
}
