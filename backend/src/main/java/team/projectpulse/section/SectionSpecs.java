package team.projectpulse.section;

import org.springframework.data.jpa.domain.Specification;

public final class SectionSpecs {
    private SectionSpecs() {
        // Utility class; not instantiated.
    }


    public static Specification<Section> containsSectionName(String providedSectionName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("sectionName")), "%" + providedSectionName.toLowerCase(java.util.Locale.ROOT) + "%");
    }

    public static Specification<Section> belongsToCourse(Integer courseId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("course").get("courseId"), courseId);
    }

    public static Specification<Section> hasActiveStatus(Boolean isActive) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("isActive"), isActive);
    }

}
