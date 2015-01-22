package lecture5.service;

import lecture5.domain.Journal;
import lecture5.domain.PageResult;
import org.springframework.stereotype.Service;

@Service
public class JournalService {
    public PageResult<Journal> getJournalsBySubjectForPage(int id, int page, int pageSize) {
        return null;
    }
}
