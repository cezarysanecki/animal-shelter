package pl.devcezz.shelter.adoption.archtecture

import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.lang.ArchRule
import org.springframework.context.annotation.Configuration

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses

@AnalyzeClasses(packages = ["pl.devcezz.shelter.adoption", "org.springframework"])
class NoSpringInDomainLogicTest {

    @ArchTest
    public static final ArchRule model_should_not_depend_on_spring =
            noClasses()
                    .that()
                    .resideInAPackage(
                            "..pl.devcezz.shelter.adoption..model..")
                    .should()
                    .dependOnClassesThat()
                    .resideInAPackage("org.springframework..")

    @ArchTest
    public static final ArchRule application_should_not_depend_on_spring =
            noClasses()
                    .that()
                    .resideInAPackage(
                            "..pl.devcezz.shelter.adoption..application..")
                    .and()
                    .areNotAnnotatedWith(Configuration.class)
                    .should()
                    .dependOnClassesThat()
                    .resideInAPackage("org.springframework..")


}