@echo off
cd "c:\Users\Public\Workspace\DLP-Multi-Tennent\work\springboot-dlp-backend\dlp-auth\temp_jar"
REM Set your Azure credentials as environment variables before running this script
REM set AZURE_CLIENT_ID=your_client_id_here
REM set AZURE_CLIENT_SECRET=your_client_secret_here
set AZURE_TENANT_ID=00fbdbac-bf96-4ddc-8685-01819c5c546d

echo Starting Spring Boot application with manual OAuth2 configuration...
java -Dhttp.nonProxyHosts="*.microsoftonline.com|*.microsoft.com|*.graph.microsoft.com" ^
     -Dhttps.nonProxyHosts="*.microsoftonline.com|*.microsoft.com|*.graph.microsoft.com" ^
     -cp "BOOT-INF/classes;BOOT-INF/lib/*" ^
     com.dlpauth.DlpMultitenantAuthGuardApp ^
     --spring.profiles.active=staging
