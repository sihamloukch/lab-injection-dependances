package ma.tp.metier;

import ma.tp.dao.IDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("metier")
public class MetierImpl implements IMetier {

    private IDao dao;

    public MetierImpl() {
    }

    // Injection via le constructeur
    public MetierImpl(@Autowired @Qualifier("d") IDao dao) {
        this.dao = dao;
    }

    @Override
    public double calcul() {
        double t = dao.getData();
        return t * 12 * Math.PI / 2 * Math.cos(t);
    }

    // Injection via le setter
    public void setDao(IDao dao) {
        this.dao = dao;
    }
}