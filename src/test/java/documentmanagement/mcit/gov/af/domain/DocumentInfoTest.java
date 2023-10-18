package documentmanagement.mcit.gov.af.domain;

import static org.assertj.core.api.Assertions.assertThat;

import documentmanagement.mcit.gov.af.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DocumentInfoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentInfo.class);
        DocumentInfo documentInfo1 = new DocumentInfo();
        documentInfo1.setId(1L);
        DocumentInfo documentInfo2 = new DocumentInfo();
        documentInfo2.setId(documentInfo1.getId());
        assertThat(documentInfo1).isEqualTo(documentInfo2);
        documentInfo2.setId(2L);
        assertThat(documentInfo1).isNotEqualTo(documentInfo2);
        documentInfo1.setId(null);
        assertThat(documentInfo1).isNotEqualTo(documentInfo2);
    }
}
