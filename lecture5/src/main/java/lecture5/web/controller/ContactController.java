package lecture5.web.controller;

import lecture5.domain.Contact;
import lecture5.service.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/contacts")
public class ContactController {

    private static final Logger logger =
            LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private ContactService contactService;

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
        logger.info("Listing contacts");

        List<Contact> contacts = contactService.findAll();
        model.addAttribute("contacts", contacts);

        logger.info("No. of contacts: {}", contacts.size());

        return "contacts/list";
    }
}
