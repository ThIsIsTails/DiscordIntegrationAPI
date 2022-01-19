import java.lang.management.ManagementFactory;

public class Print {
    public static void main(String[] args) {
        long uptime = ManagementFactory.getRuntimeMXBean().getUptime();
        long seconds = (uptime / 1000) % 60;
        long minutes = (uptime / (1000 * 60)) % 60;
        long hours = (uptime / (1000 * 60 * 60));
        System.out.println(hours + ":" + minutes + ":" + seconds);
    }
}
