package org.apereo.cas.support.saml.web.idp.profile.builders.enc;

import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.support.saml.SamlIdPUtils;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import org.opensaml.core.criterion.EntityIdCriterion;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.saml.criterion.EntityRoleCriterion;
import org.opensaml.saml.metadata.resolver.MetadataResolver;
import org.opensaml.saml.metadata.resolver.RoleDescriptorResolver;
import org.opensaml.saml.saml2.core.RequestAbstractType;
import org.opensaml.saml.saml2.metadata.IDPSSODescriptor;

import java.util.List;

/**
 * This is {@link SamlIdPObjectSignatureValidator}.
 *
 * @author Misagh Moayyed
 * @since 5.1.0
 */
@Slf4j
public class SamlIdPObjectSignatureValidator extends SamlObjectSignatureValidator {
    private final MetadataResolver casSamlIdPMetadataResolver;

    public SamlIdPObjectSignatureValidator(final List overrideSignatureReferenceDigestMethods,
                                           final List overrideSignatureAlgorithms,
                                           final List overrideBlackListedSignatureAlgorithms,
                                           final List overrideWhiteListedAlgorithms,
                                           final MetadataResolver casSamlIdPMetadataResolver,
                                           final CasConfigurationProperties casProperties) {
        super(overrideSignatureReferenceDigestMethods, overrideSignatureAlgorithms,
            overrideBlackListedSignatureAlgorithms, overrideWhiteListedAlgorithms, casProperties);
        this.casSamlIdPMetadataResolver = casSamlIdPMetadataResolver;
    }

    @Override
    protected RoleDescriptorResolver getRoleDescriptorResolver(final MetadataResolver resolver, final MessageContext context,
                                                               final RequestAbstractType profileRequest) throws Exception {

        val idp = casProperties.getAuthn().getSamlIdp();
        return SamlIdPUtils.getRoleDescriptorResolver(casSamlIdPMetadataResolver, idp.getMetadata().isRequireValidMetadata());
    }

    @Override
    protected void buildEntityCriteriaForSigningCredential(final RequestAbstractType profileRequest, final CriteriaSet criteriaSet) {
        criteriaSet.add(new EntityIdCriterion(casSamlIdPMetadataResolver.getId()));
        criteriaSet.add(new EntityRoleCriterion(IDPSSODescriptor.DEFAULT_ELEMENT_NAME));
    }
}
