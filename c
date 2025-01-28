import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.SsoCredentialsProvider;
import software.amazon.awssdk.services.sts.model.Credentials;

public class SSOCredentialFetcher {
    public static AwsCredentials getCredentials() {
        SsoCredentialsProvider credentialsProvider = SsoCredentialsProvider.builder()
                .ssoStartUrl("https://your-sso-portal.awsapps.com/start")
                .ssoRegion("us-east-1")
                .ssoAccountId("123456789012")
                .ssoRoleName("AWSAdministratorAccess")
                .build();

        Credentials credentials = credentialsProvider.resolveCredentials().resolveStsCredentials().credentials();
        
        return AwsCredentials.create(credentials.accessKeyId(), credentials.secretAccessKey());
    }
}
