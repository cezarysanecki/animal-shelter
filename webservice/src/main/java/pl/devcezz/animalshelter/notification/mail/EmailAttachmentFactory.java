package pl.devcezz.animalshelter.notification.mail;

import io.vavr.collection.List;
import pl.devcezz.animalshelter.generator.FileGeneratorFacade;
import pl.devcezz.animalshelter.generator.dto.GeneratedFileDto;
import pl.devcezz.animalshelter.notification.dto.Notification;
import pl.devcezz.animalshelter.notification.dto.Notification.FailedAcceptanceNotification;
import pl.devcezz.animalshelter.notification.dto.Notification.WarnedAcceptanceNotification;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

class EmailAttachmentFactory {

    private final FileGeneratorFacade fileGeneratorFacade;

    EmailAttachmentFactory(final FileGeneratorFacade fileGeneratorFacade) {
        this.fileGeneratorFacade = fileGeneratorFacade;
    }

    public List<EmailAttachment> createUsing(Notification notification) {
        return Match(notification).of(
                Case($(instanceOf(WarnedAcceptanceNotification.class)), this::shelterReports),
                Case($(instanceOf(FailedAcceptanceNotification.class)), this::shelterReports),
                Case($(), List::empty)
        );
    }

    private List<EmailAttachment> shelterReports() {
        GeneratedFileDto generatedPdfFile = fileGeneratorFacade.generateShelterListPdf();
        GeneratedFileDto generatedCsvFile = fileGeneratorFacade.generateShelterListCsv();

        return List.of(
                new EmailAttachment(generatedPdfFile.content(), generatedPdfFile.filename()),
                new EmailAttachment(generatedCsvFile.content(), generatedCsvFile.filename())
        );
    }
}
