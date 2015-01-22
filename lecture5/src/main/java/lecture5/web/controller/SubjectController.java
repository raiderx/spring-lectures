package lecture5.web.controller;

import lecture5.domain.Journal;
import lecture5.domain.PageResult;
import lecture5.domain.Subject;
import lecture5.service.JournalService;
import lecture5.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author Pavel Karpukhin
 * @since 30.12.14
 */
public class SubjectController {

    public static final int PAGE_SIZE = 50;

    @Autowired
    private JournalService journalService;
    @Autowired
    private SubjectService subjectService;

    @RequestMapping(value = "/subject/top.html",
                    method = RequestMethod.GET)
    public ModelAndView topSubjects() {
        List<Subject> subjects = subjectService
                .getSubjectsOrderedByNumberOfJournals();
        return new ModelAndView("subjects")
                .addObject("subjects", subjects);
    }

    @RequestMapping(value = "/subject/{id:\\d+}/journals.html",
                    method = RequestMethod.GET)
    public String subjectWithJournals(
            @PathVariable("id") int id,
            @RequestParam(value = "page", required = false,
                          defaultValue = "0") int page,
            Model model) {
        Subject subject = subjectService.getSubjectById(id);
        PageResult<Journal> journals = journalService
                .getJournalsBySubjectForPage(id, page, PAGE_SIZE);
        model.addAttribute("subject", subject)
             .addAttribute("journals", journals);

        return "subject/withJournals";
    }
}