package team.projectpulse.section;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class SectionSpecsTest {

    @Test
    @SuppressWarnings({"unchecked", "rawtypes"})
    void sectionNameSearchShouldNotDependOnSystemLocale() {
        Locale originalLocale = Locale.getDefault();

        try {
            Locale.setDefault(Locale.forLanguageTag("tr-TR"));

            Root<Section> root = mock(Root.class);
            CriteriaBuilder builder = mock(CriteriaBuilder.class);

            Path<String> nameExpression = mock(Path.class);
            Expression<String> lowerExpression = mock(Expression.class);
            Predicate predicate = mock(Predicate.class);

            when(root.<String>get("sectionName"))
                    .thenReturn(nameExpression);

            when(builder.lower(nameExpression))
                    .thenReturn(lowerExpression);

            when(builder.like(eq(lowerExpression), anyString()))
                    .thenReturn(predicate);

            Specification<Section> specification =
                    SectionSpecs.containsSectionName("INSTRUCTOR");

            specification.toPredicate(root, null, builder);

            var pattern = org.mockito.ArgumentCaptor.forClass(String.class);

            verify(builder).like(eq(lowerExpression), pattern.capture());

            assertEquals("%instructor%", pattern.getValue());

        } finally {
            Locale.setDefault(originalLocale);
        }
    }
}
