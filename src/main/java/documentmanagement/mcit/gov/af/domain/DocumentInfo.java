package documentmanagement.mcit.gov.af.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A DocumentInfo.
 */
@Entity
@Table(name = "document_info")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DocumentInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "number", nullable = false)
    private String number;

    @Column(name = "registered_number")
    private String registeredNumber;

    @Column(name = "issuedate")
    private LocalDate issuedate;

    @Column(name = "subject")
    private String subject;

    @Column(name = "dpriority")
    private String dpriority;

    @Lob
    @Column(name = "scan_path")
    private byte[] scanPath;

    @Column(name = "scan_path_content_type")
    private String scanPathContentType;

    @Column(name = "content")
    private String content;

    @Column(name = "organization")
    private String organization;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DocumentInfo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return this.number;
    }

    public DocumentInfo number(String number) {
        this.setNumber(number);
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getRegisteredNumber() {
        return this.registeredNumber;
    }

    public DocumentInfo registeredNumber(String registeredNumber) {
        this.setRegisteredNumber(registeredNumber);
        return this;
    }

    public void setRegisteredNumber(String registeredNumber) {
        this.registeredNumber = registeredNumber;
    }

    public LocalDate getIssuedate() {
        return this.issuedate;
    }

    public DocumentInfo issuedate(LocalDate issuedate) {
        this.setIssuedate(issuedate);
        return this;
    }

    public void setIssuedate(LocalDate issuedate) {
        this.issuedate = issuedate;
    }

    public String getSubject() {
        return this.subject;
    }

    public DocumentInfo subject(String subject) {
        this.setSubject(subject);
        return this;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDpriority() {
        return this.dpriority;
    }

    public DocumentInfo dpriority(String dpriority) {
        this.setDpriority(dpriority);
        return this;
    }

    public void setDpriority(String dpriority) {
        this.dpriority = dpriority;
    }

    public byte[] getScanPath() {
        return this.scanPath;
    }

    public DocumentInfo scanPath(byte[] scanPath) {
        this.setScanPath(scanPath);
        return this;
    }

    public void setScanPath(byte[] scanPath) {
        this.scanPath = scanPath;
    }

    public String getScanPathContentType() {
        return this.scanPathContentType;
    }

    public DocumentInfo scanPathContentType(String scanPathContentType) {
        this.scanPathContentType = scanPathContentType;
        return this;
    }

    public void setScanPathContentType(String scanPathContentType) {
        this.scanPathContentType = scanPathContentType;
    }

    public String getContent() {
        return this.content;
    }

    public DocumentInfo content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOrganization() {
        return this.organization;
    }

    public DocumentInfo organization(String organization) {
        this.setOrganization(organization);
        return this;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocumentInfo)) {
            return false;
        }
        return id != null && id.equals(((DocumentInfo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocumentInfo{" +
            "id=" + getId() +
            ", number='" + getNumber() + "'" +
            ", registeredNumber='" + getRegisteredNumber() + "'" +
            ", issuedate='" + getIssuedate() + "'" +
            ", subject='" + getSubject() + "'" +
            ", dpriority='" + getDpriority() + "'" +
            ", scanPath='" + getScanPath() + "'" +
            ", scanPathContentType='" + getScanPathContentType() + "'" +
            ", content='" + getContent() + "'" +
            ", organization='" + getOrganization() + "'" +
            "}";
    }
}
