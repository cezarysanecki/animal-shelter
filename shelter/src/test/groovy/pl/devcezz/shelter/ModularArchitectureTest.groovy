package pl.devcezz.shelter

import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.lang.ArchRule

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses

@AnalyzeClasses(packages = "pl.devcezz.shelter")
class ModularArchitectureTest {

    @ArchTest
    public static final ArchRule catalogue_should_not_depend_on_adoption =
            noClasses()
                    .that()
                    .resideInAPackage("..catalogue..")
                    .should()
                    .dependOnClassesThat()
                    .resideInAPackage("..adoption..")

    @ArchTest
    public static final ArchRule commons_should_not_depend_on_catalogue =
            noClasses()
                    .that()
                    .resideInAPackage("..commons..")
                    .should()
                    .dependOnClassesThat()
                    .resideInAPackage("..catalogue..")

    @ArchTest
    public static final ArchRule commons_should_not_depend_on_adoption =
            noClasses()
                    .that()
                    .resideInAPackage("..commons..")
                    .should()
                    .dependOnClassesThat()
                    .resideInAPackage("..adoption..")


}