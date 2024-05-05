package com.study.bang.license.service;

import com.study.bang.license.config.ServiceConfig;
import com.study.bang.license.model.License;
import com.study.bang.license.model.Organization;
import com.study.bang.license.repository.LicenseRepository;
import com.study.bang.license.service.client.OrganizationDiscoveryClient;
import com.study.bang.license.service.client.OrganizationFeignClient;
import com.study.bang.license.service.client.OrganizationRestTemplateClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LicenseService {

    private final MessageSource messageSource;

    private final LicenseRepository licenseRepository;

    private final ServiceConfig serviceConfig;

    // 1. Discovery Client 로 서비스 인스턴스 검색 방법
    private final OrganizationDiscoveryClient organizationDiscoveryClient;

    // 2. 로드 밸런서를 지원하는 스프링 REST 템플릿으로 서비스 호출
    private final OrganizationRestTemplateClient organizationRestClient;

    // 3. Feign 클라이언트로 서비스 호출(RestTemplate 의 대안)
    private final OrganizationFeignClient organizationFeignClient;

    public License getLicense(String licenseId, String organizationId, String clientType){
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        license = new License();
        license.setLicenseId(licenseId);
        license.setOrganizationId(organizationId);
        license.setComment(serviceConfig.getProperty());
        if (null == license) {
            throw new IllegalArgumentException(String.format(messageSource.getMessage("license.search.error.message", null, null),licenseId, organizationId));
        }

        Organization organization = retrieveOrganizationInfo(organizationId, clientType);
        if (null != organization) {
            license.setOrganizationName(organization.getName());
            license.setContactName(organization.getContactName());
            license.setContactEmail(organization.getContactEmail());
            license.setContactPhone(organization.getContactPhone());
        }

        return license.withComment(serviceConfig.getProperty());
    }

    private Organization retrieveOrganizationInfo(String organizationId, String clientType) {
        Organization organization = null;

        switch (clientType) {
            case "feign":
                System.out.println("I am using the feign client");
                organization = organizationFeignClient.getOrganization(organizationId);
                break;
            case "rest":
                System.out.println("I am using the rest client");
                organization = organizationRestClient.getOrganization(organizationId);
                break;
            case "discovery":
                System.out.println("I am using the discovery client");
                organization = organizationDiscoveryClient.getOrganization(organizationId);
                break;
            default:
                organization = organizationRestClient.getOrganization(organizationId);
        }

        return organization;
    }

}