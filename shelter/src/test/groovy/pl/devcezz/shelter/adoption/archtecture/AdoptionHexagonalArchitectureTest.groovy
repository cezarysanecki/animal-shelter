package pl.devcezz.shelter.adoption.archtecture

import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.lang.ArchRule

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses

@AnalyzeClasses(packages = "pl.devcezz.shelter.adoption")
class AdoptionHexagonalArchitectureTest {

    @ArchTest
    public static final ArchRule model_should_not_depend_on_infrastructure =
            noClasses()
                    .that()
                    .resideInAPackage("..model..")
                    .should()
                    .dependOnClassesThat()
                    .resideInAPackage("..infrastructure..")

    @ArchTest
    public static final ArchRule model_should_not_depend_on_application =
            noClasses()
                    .that()
                    .resideInAPackage("..model..")
                    .should()
                    .dependOnClassesThat()
                    .resideInAPackage("..application..")

    @ArchTest
    public static final ArchRule model_should_not_depend_on_web =
            noClasses()
                    .that()
                    .resideInAPackage("..model..")
                    .should()
                    .dependOnClassesThat()
                    .resideInAPackage("..web..")

    @ArchTest
    public static final ArchRule infrastructure_should_not_depend_on_application =
            noClasses()
                    .that()
                    .resideInAPackage("..infrastructure..")
                    .should()
                    .dependOnClassesThat()
                    .resideInAPackage("..application..")

    @ArchTest
    public static final ArchRule infrastructure_should_not_depend_on_web =
            noClasses()
                    .that()
                    .resideInAPackage("..infrastructure..")
                    .should()
                    .dependOnClassesThat()
                    .resideInAPackage("..web..")

    @ArchTest
    public static final ArchRule application_should_not_depend_on_web =
            noClasses()
                    .that()
                    .resideInAPackage("..application..")
                    .should()
                    .dependOnClassesThat()
                    .resideInAPackage("..web..")

}