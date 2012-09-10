package commonj.sdo.impl;

import org.plasma.sdo.helper.PlasmaCopyHelper;
import org.plasma.sdo.helper.PlasmaDataFactory;
import org.plasma.sdo.helper.PlasmaDataHelper;
import org.plasma.sdo.helper.PlasmaEqualityHelper;
import org.plasma.sdo.helper.PlasmaTypeHelper;
import org.plasma.sdo.helper.PlasmaXMLHelper;
import org.plasma.sdo.helper.PlasmaXSDHelper;

import commonj.sdo.helper.CopyHelper;
import commonj.sdo.helper.DataFactory;
import commonj.sdo.helper.DataHelper;
import commonj.sdo.helper.EqualityHelper;
import commonj.sdo.helper.TypeHelper;
import commonj.sdo.helper.XMLHelper;
import commonj.sdo.helper.XSDHelper;
import commonj.sdo.impl.ExternalizableDelegator.Resolvable;

public class HelperProviderImpl extends HelperProvider {
    
    public HelperProviderImpl() {
        
    }

    @Override
    public CopyHelper copyHelper() {
        return PlasmaCopyHelper.INSTANCE;
    }

    @Override
    public DataFactory dataFactory() {
        return PlasmaDataFactory.INSTANCE;
    }

    @Override
    public DataHelper dataHelper() {
        return PlasmaDataHelper.INSTANCE;
    }

    @Override
    public EqualityHelper equalityHelper() {
        return PlasmaEqualityHelper.INSTANCE;
    }

    @Override
    public Resolvable resolvable() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Resolvable resolvable(Object target) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TypeHelper typeHelper() {
        return PlasmaTypeHelper.INSTANCE;
    }

    @Override
    public XMLHelper xmlHelper() {
        return PlasmaXMLHelper.INSTANCE;
    }

    @Override
    public XSDHelper xsdHelper() {
        return PlasmaXSDHelper.INSTANCE;
    }

}