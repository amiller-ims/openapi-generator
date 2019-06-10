package org.openapitools.codegen.languages;

import com.samskivert.mustache.Mustache;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.parser.util.SchemaTypeUtil;
import org.openapitools.codegen.*;
import io.swagger.models.properties.ArrayProperty;
import io.swagger.models.properties.MapProperty;
import io.swagger.models.properties.Property;
import io.swagger.models.parameters.Parameter;

import java.io.File;
import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.openapitools.codegen.utils.URLPathUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;

import static java.util.UUID.randomUUID;

public class AspNetCoreIs4ServerCodegen extends AbstractCSharpCodegen {

    public static final String USE_SWASHBUCKLE = "useSwashbuckle";
    public static final String ASPNET_CORE_VERSION = "aspnetCoreVersion";
    public static final String CLASS_MODIFIER = "classModifier";
    public static final String OPERATION_MODIFIER = "operationModifier";
    public static final String OPERATION_IS_ASYNC = "operationIsAsync";
    public static final String OPERATION_RESULT_TASK = "operationResultTask";
    public static final String GENERATE_BODY = "generateBody";
    public static final String BUILD_TARGET = "buildTarget";
    public static final String MODEL_CLASS_MODIFIER = "modelClassModifier";

    public static final String PROJECT_SDK = "projectSdk";
    public static final String SDK_WEB = "Microsoft.NET.Sdk.Web";
    public static final String SDK_LIB = "Microsoft.NET.Sdk";
    public static final String COMPATIBILITY_VERSION = "compatibilityVersion";
    public static final String IS_LIBRARY = "isLibrary";

    private String packageGuid = "{" + randomUUID().toString().toUpperCase(Locale.ROOT) + "}";

    @SuppressWarnings("hiding")
    protected Logger LOGGER = LoggerFactory.getLogger(AspNetCoreServerCodegen.class);

    private boolean useSwashbuckle = true;
    protected int serverPort = 8080;
    protected String serverHost = "0.0.0.0";
    protected CliOption aspnetCoreVersion = new CliOption(ASPNET_CORE_VERSION, "ASP.NET Core version: 2.2 (default), 2.1");
    ; // default to 2.2
    private CliOption classModifier = new CliOption(CLASS_MODIFIER, "Class Modifier can be empty, abstract");
    private CliOption operationModifier = new CliOption(OPERATION_MODIFIER, "Operation Modifier can be virtual, abstract or partial");
    private CliOption modelClassModifier = new CliOption(MODEL_CLASS_MODIFIER, "Model Class Modifier can be nothing or partial");
    private boolean generateBody = true;
    private CliOption buildTarget = new CliOption("buildTarget", "Target to build an application or library");
    private String projectSdk = SDK_WEB;
    private String compatibilityVersion = "Version_2_2";
    private boolean operationIsAsync = false;
    private boolean operationResultTask = false;
    private boolean isLibrary = false;

    public AspNetCoreIs4ServerCodegen() {
        super();

        outputFolder = "generated-code" + File.separator + getName();

        modelTemplateFiles.put("model.mustache", ".cs");
        apiTemplateFiles.put("controller.mustache", ".cs");

        // contextually reserved words
        // NOTE: C# uses camel cased reserved words, while models are title cased. We don't want lowercase comparisons.
        reservedWords.addAll(
                Arrays.asList("var", "async", "await", "dynamic", "yield")
        );

        cliOptions.clear();

        typeMapping.put("boolean", "bool");
        typeMapping.put("integer", "int");
        typeMapping.put("float", "float");
        typeMapping.put("long", "long");
        typeMapping.put("double", "double");
        typeMapping.put("number", "decimal");
        typeMapping.put("DateTime", "DateTime");
        typeMapping.put("date", "DateTime");
        typeMapping.put("UUID", "Guid");
        typeMapping.put("URI", "string");

        setSupportNullable(Boolean.TRUE);

        // CLI options
        addOption(CodegenConstants.LICENSE_URL,
                CodegenConstants.LICENSE_URL_DESC,
                licenseUrl);

        addOption(CodegenConstants.LICENSE_NAME,
                CodegenConstants.LICENSE_NAME_DESC,
                licenseName);

        addOption(CodegenConstants.PACKAGE_COPYRIGHT,
                CodegenConstants.PACKAGE_COPYRIGHT_DESC,
                packageCopyright);

        addOption(CodegenConstants.PACKAGE_AUTHORS,
                CodegenConstants.PACKAGE_AUTHORS_DESC,
                packageAuthors);

        addOption(CodegenConstants.PACKAGE_TITLE,
                CodegenConstants.PACKAGE_TITLE_DESC,
                packageTitle);

        addOption(CodegenConstants.PACKAGE_NAME,
                "C# package name (convention: Title.Case).",
                packageName);

        addOption(CodegenConstants.PACKAGE_VERSION,
                "C# package version.",
                packageVersion);

        addOption(CodegenConstants.OPTIONAL_PROJECT_GUID,
                CodegenConstants.OPTIONAL_PROJECT_GUID_DESC,
                null);

        addOption(CodegenConstants.SOURCE_FOLDER,
                CodegenConstants.SOURCE_FOLDER_DESC,
                sourceFolder);

        addOption(COMPATIBILITY_VERSION, "ASP.Net Core CompatibilityVersion", compatibilityVersion);

        aspnetCoreVersion.addEnum("2.1", "ASP.NET Core 2.1");
        aspnetCoreVersion.addEnum("2.2", "ASP.NET Core 2.2");
        aspnetCoreVersion.setDefault("2.2");
        aspnetCoreVersion.setOptValue(aspnetCoreVersion.getDefault());
        addOption(aspnetCoreVersion.getOpt(), aspnetCoreVersion.getDescription(), aspnetCoreVersion.getOptValue());

        // CLI Switches
        addSwitch(CodegenConstants.SORT_PARAMS_BY_REQUIRED_FLAG,
                CodegenConstants.SORT_PARAMS_BY_REQUIRED_FLAG_DESC,
                sortParamsByRequiredFlag);

        addSwitch(CodegenConstants.USE_DATETIME_OFFSET,
                CodegenConstants.USE_DATETIME_OFFSET_DESC,
                useDateTimeOffsetFlag);

        addSwitch(CodegenConstants.USE_COLLECTION,
                CodegenConstants.USE_COLLECTION_DESC,
                useCollection);

        addSwitch(CodegenConstants.RETURN_ICOLLECTION,
                CodegenConstants.RETURN_ICOLLECTION_DESC,
                returnICollection);

        addSwitch(USE_SWASHBUCKLE,
                "Uses the Swashbuckle.AspNetCore NuGet package for documentation.",
                useSwashbuckle);

        addSwitch(IS_LIBRARY,
                "Is the build a library",
                isLibrary);

        classModifier.addEnum("", "Keep class default with no modifier");
        classModifier.addEnum("abstract", "Make class abstract");
        classModifier.setDefault("");
        classModifier.setOptValue(classModifier.getDefault());
        addOption(classModifier.getOpt(), classModifier.getDescription(), classModifier.getOptValue());

        operationModifier.addEnum("virtual", "Keep method virtual");
        operationModifier.addEnum("abstract", "Make method abstract");
        operationModifier.setDefault("virtual");
        operationModifier.setOptValue(operationModifier.getDefault());
        addOption(operationModifier.getOpt(), operationModifier.getDescription(), operationModifier.getOptValue());

        buildTarget.addEnum("program", "Generate code for a standalone server");
        buildTarget.addEnum("library", "Generate code for a server abstract class lbrary");
        buildTarget.setDefault("program");
        buildTarget.setOptValue(buildTarget.getDefault());
        addOption(buildTarget.getOpt(), buildTarget.getDescription(), buildTarget.getOptValue());

        addSwitch(GENERATE_BODY,
                "Generates method body.",
                generateBody);

        addSwitch(OPERATION_IS_ASYNC,
                "Set methods to async or sync (default).",
                operationIsAsync);

        addSwitch(OPERATION_RESULT_TASK,
                "Set methods result to Task<>.",
                operationResultTask);

        modelClassModifier.setType("String");
        modelClassModifier.addEnum("", "Keep model class default with no modifier");
        modelClassModifier.addEnum("partial", "Make model class partial");
        modelClassModifier.setDefault("partial");
        modelClassModifier.setOptValue(modelClassModifier.getDefault());
        addOption(modelClassModifier.getOpt(), modelClassModifier.getDescription(), modelClassModifier.getOptValue());
    }

    @Override
    public CodegenType getTag() {
        return CodegenType.SERVER;
    }

    @Override
    public String getName() {
        return "aspnetcore-is4";
    }

    @Override
    public String getHelp() {
        return "Generates an ASP.NET Core Web API server with Identity Server 4.";
    }

    @Override
    public void preprocessOpenAPI(OpenAPI openAPI) {
        super.preprocessOpenAPI(openAPI);
        URL url = URLPathUtils.getServerURL(openAPI);
        additionalProperties.put("serverHost", url.getHost());
        additionalProperties.put("serverPort", URLPathUtils.getPort(url, 8080));
    }

    @Override
    public void processOpts() {
        super.processOpts();

        if (additionalProperties.containsKey(CodegenConstants.OPTIONAL_PROJECT_GUID)) {
            setPackageGuid((String) additionalProperties.get(CodegenConstants.OPTIONAL_PROJECT_GUID));
        }
        additionalProperties.put("packageGuid", packageGuid);


        // CHeck for the modifiers etc.
        // The order of the checks is important.
        setBuildTarget();
        setClassModifier();
        setOperationModifier();
        setModelClassModifier();
        setUseSwashbuckle();
        setOperationIsAsync();

        // CHeck for class modifier if not present set the default value.
        additionalProperties.put(PROJECT_SDK, projectSdk);

        additionalProperties.put("dockerTag", packageName.toLowerCase(Locale.ROOT));

        if (!additionalProperties.containsKey(CodegenConstants.API_PACKAGE)) {
            apiPackage = packageName + ".Controllers";
            additionalProperties.put(CodegenConstants.API_PACKAGE, apiPackage);
        }

        if (!additionalProperties.containsKey(CodegenConstants.MODEL_PACKAGE)) {
            modelPackage = packageName + ".Models";
            additionalProperties.put(CodegenConstants.MODEL_PACKAGE, modelPackage);
        }

        String packageFolder = sourceFolder + File.separator + packageName;

        // determine the ASP.NET core version setting
        setAspnetCoreVersion();

        supportingFiles.add(new SupportingFile("build.sh.mustache", "", "build.sh"));
        supportingFiles.add(new SupportingFile("build.bat.mustache", "", "build.bat"));
        supportingFiles.add(new SupportingFile("README.mustache", "", "README.md"));
        supportingFiles.add(new SupportingFile("Solution.mustache", "", packageName + ".sln"));
        supportingFiles.add(new SupportingFile("gitignore", packageFolder, ".gitignore"));
        supportingFiles.add(new SupportingFile("validateModel.mustache", packageFolder + File.separator + "Attributes", "ValidateModelStateAttribute.cs"));
        supportingFiles.add(new SupportingFile("Project.csproj.mustache", packageFolder, packageName + ".csproj"));
        if (!isLibrary) {
            supportingFiles.add(new SupportingFile("Dockerfile.mustache", packageFolder, "Dockerfile"));
            supportingFiles.add(new SupportingFile("appsettings.json", packageFolder, "appsettings.json"));

            supportingFiles.add(new SupportingFile("Startup.mustache", packageFolder, "Startup.cs"));
            supportingFiles.add(new SupportingFile("Program.mustache", packageFolder, "Program.cs"));
            supportingFiles.add(new SupportingFile("Properties" + File.separator + "launchSettings.json",
                    packageFolder + File.separator + "Properties", "launchSettings.json"));
            // wwwroot files.
            supportingFiles.add(new SupportingFile("wwwroot" + File.separator + "openapi-original.mustache",
                    packageFolder + File.separator + "wwwroot", "openapi-original.json"));
            
            // IdentityServer4 files.
            supportingFiles.add(new SupportingFile("Config.mustache", packageFolder, "Config.cs"));

            supportingFiles.add(new SupportingFile("Attributes" + File.separator + "ValidateModelStateAttribute.mustache",
                packageFolder + File.separator + "Attributes", "ValidateModelStateAttribute.cs"));
            supportingFiles.add(new SupportingFile("Quickstart" + File.separator + "Extensions.cs", 
                packageFolder + File.separator + "Quickstart", "Extensions.cs"));
            supportingFiles.add(new SupportingFile("Quickstart" + File.separator + "SecurityHeadersAttribute.cs", 
                packageFolder + File.separator + "Quickstart", "SecurityHeadersAttribute.cs"));
            supportingFiles.add(new SupportingFile("Quickstart" + File.separator + "TestUsers.cs", 
                packageFolder + File.separator + "Quickstart", "TestUsers.cs"));

            supportingFiles.add(new SupportingFile("Quickstart" + File.separator + "Account" + File.separator + "AccountController.cs", 
                packageFolder + File.separator + "Quickstart" + File.separator + "Account", "AccountController.cs"));
            supportingFiles.add(new SupportingFile("Quickstart" + File.separator + "Account" + File.separator + "AccountOptions.cs", 
                packageFolder + File.separator + "Quickstart" + File.separator + "Account", "AccountOptions.cs"));
            supportingFiles.add(new SupportingFile("Quickstart" + File.separator + "Account" + File.separator + "ExternalController.cs", 
                packageFolder + File.separator + "Quickstart" + File.separator + "Account", "ExternalController.cs"));
            supportingFiles.add(new SupportingFile("Quickstart" + File.separator + "Account" + File.separator + "ExternalProvider.cs", 
                packageFolder + File.separator + "Quickstart" + File.separator + "Account", "ExternalProvider.cs"));
            supportingFiles.add(new SupportingFile("Quickstart" + File.separator + "Account" + File.separator + "LoggedOutViewModel.cs", 
                packageFolder + File.separator + "Quickstart" + File.separator + "Account", "LoggedOutViewModel.cs"));
            supportingFiles.add(new SupportingFile("Quickstart" + File.separator + "Account" + File.separator + "LoginInputModel.cs", 
                packageFolder + File.separator + "Quickstart" + File.separator + "Account", "LoginInputModel.cs"));
            supportingFiles.add(new SupportingFile("Quickstart" + File.separator + "Account" + File.separator + "LoginViewModel.cs", 
                packageFolder + File.separator + "Quickstart" + File.separator + "Account", "LoginViewModel.cs"));
            supportingFiles.add(new SupportingFile("Quickstart" + File.separator + "Account" + File.separator + "LogoutInputModel.cs", 
                packageFolder + File.separator + "Quickstart" + File.separator + "Account", "LogoutInputModel.cs"));
            supportingFiles.add(new SupportingFile("Quickstart" + File.separator + "Account" + File.separator + "LogoutViewModel.cs", 
                packageFolder + File.separator + "Quickstart" + File.separator + "Account", "LogoutViewModel.cs"));
            supportingFiles.add(new SupportingFile("Quickstart" + File.separator + "Account" + File.separator + "RedirectViewModel.cs", 
                packageFolder + File.separator + "Quickstart" + File.separator + "Account", "RedirectViewModel.cs"));

            supportingFiles.add(new SupportingFile("Quickstart" + File.separator + "Consent" + File.separator + "ConsentController.cs", 
                packageFolder + File.separator + "Quickstart" + File.separator + "Consent", "ConsentController.cs"));
            supportingFiles.add(new SupportingFile("Quickstart" + File.separator + "Consent" + File.separator + "ConsentInputModel.cs", 
                packageFolder + File.separator + "Quickstart" + File.separator + "Consent", "ConsentInputModel.cs"));
            supportingFiles.add(new SupportingFile("Quickstart" + File.separator + "Consent" + File.separator + "ConsentOptions.cs", 
                packageFolder + File.separator + "Quickstart" + File.separator + "Consent", "ConsentOptions.cs"));
            supportingFiles.add(new SupportingFile("Quickstart" + File.separator + "Consent" + File.separator + "ConsentViewModel.cs", 
                packageFolder + File.separator + "Quickstart" + File.separator + "Consent", "ConsentViewModel.cs"));
            supportingFiles.add(new SupportingFile("Quickstart" + File.separator + "Consent" + File.separator + "ProcessConsentResult.cs", 
                packageFolder + File.separator + "Quickstart" + File.separator + "Consent", "ProcessConsentResult.cs"));
            supportingFiles.add(new SupportingFile("Quickstart" + File.separator + "Consent" + File.separator + "ScopeViewModel.cs", 
                packageFolder + File.separator + "Quickstart" + File.separator + "Consent", "ScopeViewModel.cs"));

            supportingFiles.add(new SupportingFile("Quickstart" + File.separator + "Device" + File.separator + "DeviceAuthorizationInputModel.cs", 
                packageFolder + File.separator + "Quickstart" + File.separator + "Device", "DeviceAuthorizationInputModel.cs"));
            supportingFiles.add(new SupportingFile("Quickstart" + File.separator + "Device" + File.separator + "DeviceAuthorizationViewModel.cs", 
                packageFolder + File.separator + "Quickstart" + File.separator + "Device", "DeviceAuthorizationViewModel.cs"));
            supportingFiles.add(new SupportingFile("Quickstart" + File.separator + "Device" + File.separator + "DeviceController.cs", 
                packageFolder + File.separator + "Quickstart" + File.separator + "Device", "DeviceController.cs"));

            supportingFiles.add(new SupportingFile("Quickstart" + File.separator + "Diagnostics" + File.separator + "DiagnosticsController.cs", 
                packageFolder + File.separator + "Quickstart" + File.separator + "Diagnostics", "DiagnosticsController.cs"));
            supportingFiles.add(new SupportingFile("Quickstart" + File.separator + "Diagnostics" + File.separator + "DiagnosticsViewModel.cs", 
                packageFolder + File.separator + "Quickstart" + File.separator + "Diagnostics", "DiagnosticsViewModel.cs"));

            supportingFiles.add(new SupportingFile("Quickstart" + File.separator + "Grants" + File.separator + "GrantsController.cs", 
                packageFolder + File.separator + "Quickstart" + File.separator + "Grants", "GrantsController.cs"));
            supportingFiles.add(new SupportingFile("Quickstart" + File.separator + "Grants" + File.separator + "GrantsViewModel.cs", 
                packageFolder + File.separator + "Quickstart" + File.separator + "Grants", "GrantsViewModel.cs"));

            supportingFiles.add(new SupportingFile("Quickstart" + File.separator + "Home" + File.separator + "ErrorViewModel.cs", 
                packageFolder + File.separator + "Quickstart" + File.separator + "Home", "ErrorViewModel.cs"));
            supportingFiles.add(new SupportingFile("Quickstart" + File.separator + "Home" + File.separator + "HomeController.cs", 
                packageFolder + File.separator + "Quickstart" + File.separator + "Home", "HomeController.cs"));

            supportingFiles.add(new SupportingFile("Views" + File.separator + "_ViewImports.cshtml", 
                packageFolder + File.separator + "Views", "_ViewImports.cshtml"));
            supportingFiles.add(new SupportingFile("Views" + File.separator + "_ViewStart.cshtml", 
                packageFolder + File.separator + "Views", "_ViewStart.cshtml"));

            supportingFiles.add(new SupportingFile("Views" + File.separator + "Account" + File.separator + "LoggedOut.cshtml", 
                packageFolder + File.separator + "Views" + File.separator + "Account", "LoggedOut.cshtml"));
            supportingFiles.add(new SupportingFile("Views" + File.separator + "Account" + File.separator + "Login.cshtml", 
                packageFolder + File.separator + "Views" + File.separator + "Account", "Login.cshtml"));
            supportingFiles.add(new SupportingFile("Views" + File.separator + "Account" + File.separator + "Logout.cshtml", 
                packageFolder + File.separator + "Views" + File.separator + "Account", "Logout.cshtml"));

            supportingFiles.add(new SupportingFile("Views" + File.separator + "Consent" + File.separator + "_ScopeListItem.cshtml", 
                packageFolder + File.separator + "Views" + File.separator + "Consent", "_ScopeListItem.cshtml"));
            supportingFiles.add(new SupportingFile("Views" + File.separator + "Consent" + File.separator + "Index.cshtml", 
                packageFolder + File.separator + "Views" + File.separator + "Consent", "Index.cshtml"));

            supportingFiles.add(new SupportingFile("Views" + File.separator + "Device" + File.separator + "Success.cshtml", 
                packageFolder + File.separator + "Views" + File.separator + "Device", "Success.cshtml"));
            supportingFiles.add(new SupportingFile("Views" + File.separator + "Device" + File.separator + "UserCodeCapture.cshtml", 
                packageFolder + File.separator + "Views" + File.separator + "Device", "UserCodeCapture.cshtml"));
            supportingFiles.add(new SupportingFile("Views" + File.separator + "Device" + File.separator + "UserCodeConfirmation.cshtml", 
                packageFolder + File.separator + "Views" + File.separator + "Device", "UserCodeConfirmation.cshtml"));

            supportingFiles.add(new SupportingFile("Views" + File.separator + "Diagnostics" + File.separator + "Index.cshtml", 
                packageFolder + File.separator + "Views" + File.separator + "Diagnostics", "Index.cshtml"));

            supportingFiles.add(new SupportingFile("Views" + File.separator + "Grants" + File.separator + "Index.cshtml", 
                packageFolder + File.separator + "Views" + File.separator + "Grants", "Index.cshtml"));

            supportingFiles.add(new SupportingFile("Views" + File.separator + "Home" + File.separator + "Index.cshtml", 
                packageFolder + File.separator + "Views" + File.separator + "Home", "Index.cshtml"));

            supportingFiles.add(new SupportingFile("Views" + File.separator + "Shared" + File.separator + "_Layout.cshtml", 
                packageFolder + File.separator + "Views" + File.separator + "Shared", "_Layout.cshtml"));
            supportingFiles.add(new SupportingFile("Views" + File.separator + "Shared" + File.separator + "_ScopeListItem.cshtml", 
                packageFolder + File.separator + "Views" + File.separator + "Shared", "_ScopeListItem.cshtml"));
            supportingFiles.add(new SupportingFile("Views" + File.separator + "Shared" + File.separator + "_ValidationSummary.cshtml", 
                packageFolder + File.separator + "Views" + File.separator + "Shared", "_ValidationSummary.cshtml"));
            supportingFiles.add(new SupportingFile("Views" + File.separator + "Shared" + File.separator + "Error.cshtml", 
                packageFolder + File.separator + "Views" + File.separator + "Shared", "Error.cshtml"));
            supportingFiles.add(new SupportingFile("Views" + File.separator + "Shared" + File.separator + "Redirect.cshtml", 
                packageFolder + File.separator + "Views" + File.separator + "Shared", "Redirect.cshtml"));
        } else {
            supportingFiles.add(new SupportingFile("Project.nuspec.mustache", packageFolder, packageName + ".nuspec"));
        }


        if (useSwashbuckle) {
            supportingFiles.add(new SupportingFile("Filters" + File.separator + "BasePathFilter.mustache",
                    packageFolder + File.separator + "Filters", "BasePathFilter.cs"));
            supportingFiles.add(new SupportingFile("Filters" + File.separator + "GeneratePathParamsValidationFilter.mustache",
                    packageFolder + File.separator + "Filters", "GeneratePathParamsValidationFilter.cs"));
        }
    }

    public void setPackageGuid(String packageGuid) {
        this.packageGuid = packageGuid;
    }

    @Override
    public String apiFileFolder() {
        return outputFolder + File.separator + sourceFolder + File.separator + packageName + File.separator + "Controllers";
    }

    @Override
    public String modelFileFolder() {
        return outputFolder + File.separator + sourceFolder + File.separator + packageName + File.separator + "Models";
    }

    @Override
    public Map<String, Object> postProcessSupportingFileData(Map<String, Object> objs) {
        generateJSONSpecFile(objs);
        return super.postProcessSupportingFileData(objs);
    }

    @Override
    protected void processOperation(CodegenOperation operation) {
        super.processOperation(operation);

        // HACK: Unlikely in the wild, but we need to clean operation paths for MVC Routing
        if (operation.path != null) {
            String original = operation.path;
            operation.path = operation.path.replace("?", "/");
            if (!original.equals(operation.path)) {
                LOGGER.warn("Normalized " + original + " to " + operation.path + ". Please verify generated source.");
            }
        }

        // Converts, for example, PUT to HttpPut for controller attributes
        operation.httpMethod = "Http" + operation.httpMethod.substring(0, 1) + operation.httpMethod.substring(1).toLowerCase(Locale.ROOT);
    }

    @Override
    public Mustache.Compiler processCompiler(Mustache.Compiler compiler) {
        // To avoid unexpected behaviors when options are passed programmatically such as { "useCollection": "" }
        return super.processCompiler(compiler).emptyStringIsFalse(true);
    }

    @Override
    public String toRegularExpression(String pattern) {
        return escapeText(pattern);
    }

    @Override
    public String getNullableType(Schema p, String type) {
        boolean isNullableExpected = p.getNullable() == null || (p.getNullable() != null && p.getNullable());

        if (isNullableExpected && languageSpecificPrimitives.contains(type + "?")) {
            return type + "?";
        } else if (languageSpecificPrimitives.contains(type)) {
            return type;
        } else {
            return null;
        }
    }

    private void setCliOption(CliOption cliOption) throws IllegalArgumentException {
        if (additionalProperties.containsKey(cliOption.getOpt())) {
            // TODO Hack - not sure why the empty strings become boolean.
            Object obj = additionalProperties.get(cliOption.getOpt());
            if (!SchemaTypeUtil.BOOLEAN_TYPE.equals(cliOption.getType())) {
                if (obj instanceof Boolean) {
                    obj = "";
                    additionalProperties.put(cliOption.getOpt(), obj);
                }
            }
            cliOption.setOptValue(obj.toString());
        } else {
            additionalProperties.put(cliOption.getOpt(), cliOption.getOptValue());
        }
        if (cliOption.getOptValue() == null) {
            cliOption.setOptValue(cliOption.getDefault());
            throw new IllegalArgumentException(cliOption.getOpt() + ": Invalid value '" + additionalProperties.get(cliOption.getOpt()).toString() + "'" +
                    ". " + cliOption.getDescription());
        }
    }

    private void setClassModifier() {
        // CHeck for class modifier if not present set the default value.
        setCliOption(classModifier);

        // If class modifier is abstract then the methods need to be abstract too.
        if ("abstract".equals(classModifier.getOptValue())) {
            operationModifier.setOptValue(classModifier.getOptValue());
            additionalProperties.put(OPERATION_MODIFIER, operationModifier.getOptValue());
            LOGGER.warn("classModifier is " + classModifier.getOptValue() + " so forcing operatonModifier to " + operationModifier.getOptValue());
        }
    }

    private void setOperationModifier() {
        setCliOption(operationModifier);

        // If operation modifier is abstract then dont generate any body
        if ("abstract".equals(operationModifier.getOptValue())) {
            generateBody = false;
            additionalProperties.put(GENERATE_BODY, generateBody);
            LOGGER.warn("operationModifier is " + operationModifier.getOptValue() + " so forcing generateBody to " + generateBody);
        } else if (additionalProperties.containsKey(GENERATE_BODY)) {
            generateBody = convertPropertyToBooleanAndWriteBack(GENERATE_BODY);
        } else {
            additionalProperties.put(GENERATE_BODY, generateBody);
        }
    }

    private void setModelClassModifier() {
        setCliOption(modelClassModifier);

        // If operation modifier is abstract then dont generate any body
        if (isLibrary) {
            modelClassModifier.setOptValue("");
            additionalProperties.put(MODEL_CLASS_MODIFIER, modelClassModifier.getOptValue());
            LOGGER.warn("buildTarget is " + buildTarget.getOptValue() + " so removing any modelClassModifier ");
        }
    }

    private void setBuildTarget() {
        setCliOption(buildTarget);
        if ("library".equals(buildTarget.getOptValue())) {
            isLibrary = true;
            projectSdk = SDK_LIB;
            additionalProperties.put(CLASS_MODIFIER, "abstract");
        } else {
            isLibrary = false;
            projectSdk = SDK_WEB;
        }
        additionalProperties.put(IS_LIBRARY, isLibrary);
    }

    private void setAspnetCoreVersion() {
        setCliOption(aspnetCoreVersion);
        LOGGER.info("ASP.NET core version: " + aspnetCoreVersion.getOptValue());
        compatibilityVersion = "Version_" + aspnetCoreVersion.getOptValue().replace(".", "_");
        additionalProperties.put(COMPATIBILITY_VERSION, compatibilityVersion);
    }

    private void setUseSwashbuckle() {
        if (isLibrary) {
            LOGGER.warn("buildTarget is " + buildTarget.getOptValue() + " so changing default isLibrary to false ");
            useSwashbuckle = false;
        } else {
            useSwashbuckle = true;
        }
        if (additionalProperties.containsKey(USE_SWASHBUCKLE)) {
            useSwashbuckle = convertPropertyToBooleanAndWriteBack(USE_SWASHBUCKLE);
        } else {
            additionalProperties.put(USE_SWASHBUCKLE, useSwashbuckle);
        }
    }

    private void setOperationIsAsync() {
        if (isLibrary) {
            operationIsAsync = false;
            additionalProperties.put(OPERATION_IS_ASYNC, operationIsAsync);
        } else  if (additionalProperties.containsKey(OPERATION_IS_ASYNC)) {
            operationIsAsync = convertPropertyToBooleanAndWriteBack(OPERATION_IS_ASYNC);
        } else {
            additionalProperties.put(OPERATION_IS_ASYNC, operationIsAsync);
        }
    }
}
