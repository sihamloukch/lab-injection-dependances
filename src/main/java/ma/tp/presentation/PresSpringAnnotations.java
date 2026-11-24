package ma.tp.presentation;

import ma.tp.metier.IMetier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class PresSpringAnnotations {
    public static void main(String[] args) {
        // Spring scanne ces packages à la recherche de @Component
        ApplicationContext context =
                new AnnotationConfigApplicationContext("ma.tp.dao", "ma.tp.metier");
        IMetier metier = context.getBean(IMetier.class);
        System.out.println("Résultat = " + metier.calcul());
    }
}