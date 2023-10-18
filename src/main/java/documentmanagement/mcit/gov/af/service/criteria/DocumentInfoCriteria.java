package documentmanagement.mcit.gov.af.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link documentmanagement.mcit.gov.af.domain.DocumentInfo} entity. This class is used
 * in {@link documentmanagement.mcit.gov.af.web.rest.DocumentInfoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /document-infos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DocumentInfoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter number;

    private StringFilter registeredNumber;

    private LocalDateFilter issuedate;

    private StringFilter subject;

    private StringFilter dpriority;

    private StringFilter content;

    private StringFilter organization;

    private Boolean distinct;

    public DocumentInfoCriteria() {}

    public DocumentInfoCriteria(DocumentInfoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.number = other.number == null ? null : other.number.copy();
        this.registeredNumber = other.registeredNumber == null ? null : other.registeredNumber.copy();
        this.issuedate = other.issuedate == null ? null : other.issuedate.copy();
        this.subject = other.subject == null ? null : other.subject.copy();
        this.dpriority = other.dpriority == null ? null : other.dpriority.copy();
        this.content = other.content == null ? null : other.content.copy();
        this.organization = other.organization == null ? null : other.organization.copy();
        this.distinct = other.distinct;
    }

    @Override
    public DocumentInfoCriteria copy() {
        return new DocumentInfoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNumber() {
        return number;
    }

    public StringFilter number() {
        if (number == null) {
            number = new StringFilter();
        }
        return number;
    }

    public void setNumber(StringFilter number) {
        this.number = number;
    }

    public StringFilter getRegisteredNumber() {
        return registeredNumber;
    }

    public StringFilter registeredNumber() {
        if (registeredNumber == null) {
            registeredNumber = new StringFilter();
        }
        return registeredNumber;
    }

    public void setRegisteredNumber(StringFilter registeredNumber) {
        this.registeredNumber = registeredNumber;
    }

    public LocalDateFilter getIssuedate() {
        return issuedate;
    }

    public LocalDateFilter issuedate() {
        if (issuedate == null) {
            issuedate = new LocalDateFilter();
        }
        return issuedate;
    }

    public void setIssuedate(LocalDateFilter issuedate) {
        this.issuedate = issuedate;
    }

    public StringFilter getSubject() {
        return subject;
    }

    public StringFilter subject() {
        if (subject == null) {
            subject = new StringFilter();
        }
        return subject;
    }

    public void setSubject(StringFilter subject) {
        this.subject = subject;
    }

    public StringFilter getDpriority() {
        return dpriority;
    }

    public StringFilter dpriority() {
        if (dpriority == null) {
            dpriority = new StringFilter();
        }
        return dpriority;
    }

    public void setDpriority(StringFilter dpriority) {
        this.dpriority = dpriority;
    }

    public StringFilter getContent() {
        return content;
    }

    public StringFilter content() {
        if (content == null) {
            content = new StringFilter();
        }
        return content;
    }

    public void setContent(StringFilter content) {
        this.content = content;
    }

    public StringFilter getOrganization() {
        return organization;
    }

    public StringFilter organization() {
        if (organization == null) {
            organization = new StringFilter();
        }
        return organization;
    }

    public void setOrganization(StringFilter organization) {
        this.organization = organization;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DocumentInfoCriteria that = (DocumentInfoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(number, that.number) &&
            Objects.equals(registeredNumber, that.registeredNumber) &&
            Objects.equals(issuedate, that.issuedate) &&
            Objects.equals(subject, that.subject) &&
            Objects.equals(dpriority, that.dpriority) &&
            Objects.equals(content, that.content) &&
            Objects.equals(organization, that.organization) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, registeredNumber, issuedate, subject, dpriority, content, organization, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocumentInfoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (number != null ? "number=" + number + ", " : "") +
            (registeredNumber != null ? "registeredNumber=" + registeredNumber + ", " : "") +
            (issuedate != null ? "issuedate=" + issuedate + ", " : "") +
            (subject != null ? "subject=" + subject + ", " : "") +
            (dpriority != null ? "dpriority=" + dpriority + ", " : "") +
            (content != null ? "content=" + content + ", " : "") +
            (organization != null ? "organization=" + organization + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
