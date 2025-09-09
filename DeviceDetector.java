import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeviceDetector {
    
    // Expressões regulares para detectar dispositivos móveis
    private static final String MOBILE_REGEX = "(?i)(android|bb\\d+|meego).+mobile|avantgo|bada\\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|mobile.+firefox|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\\.(browser|link)|vodafone|wap|windows ce|xda|xiino";
    private static final String TABLET_REGEX = "(?i)(android|bb\\d+|meego).+mobile|avantgo|bada\\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|mobile.+firefox|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\\.(browser|link)|vodafone|wap|windows ce|xda|xiino|android|ipad|playbook|silk";
    
    public static String detectDeviceType(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        String accept = request.getHeader("Accept");
        
        if (userAgent == null || accept == null) {
            return "desktop";
        }
        
        // Compilar padrões
        Pattern mobilePattern = Pattern.compile(MOBILE_REGEX, Pattern.CASE_INSENSITIVE);
        Pattern tabletPattern = Pattern.compile(TABLET_REGEX, Pattern.CASE_INSENSITIVE);
        
        // Verificar se é tablet
        Matcher tabletMatcher = tabletPattern.matcher(userAgent);
        if (tabletMatcher.find()) {
            return "tablet";
        }
        
        // Verificar se é mobile
        Matcher mobileMatcher = mobilePattern.matcher(userAgent);
        if (mobileMatcher.find()) {
            return "mobile";
        }
        
        // Verificar por headers específicos
        if (userAgent.contains("Android") && !userAgent.contains("Mobile")) {
            return "tablet";
        }
        
        if (userAgent.contains("iPad") || userAgent.contains("PlayBook")) {
            return "tablet";
        }
        
        // Verificar por accept headers
        if (accept.contains("application/vnd.wap.xhtml+xml")) {
            return "mobile";
        }
        
        // Verificar por headers HTTP específicos
        String xWapProfile = request.getHeader("x-wap-profile");
        String profile = request.getHeader("Profile");
        
        if (xWapProfile != null || profile != null) {
            return "mobile";
        }
        
        return "desktop";
    }
}
