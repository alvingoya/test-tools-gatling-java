import io.gatling.recorder.GatlingRecorder;
import io.gatling.recorder.config.RecorderPropertiesBuilder;
import scala.Option;

public class Recorder {
  public static void main(String[] args) {
    RecorderPropertiesBuilder props = new RecorderPropertiesBuilder()
      .simulationsFolder(IDEPathHelper.mavenSourcesDirectory.toString())
      .resourcesFolder(IDEPathHelper.mavenResourcesDirectory.toString())
      .simulationPackage("game.test.suite.simulation.game99")
      .simulationFormatJava();

    GatlingRecorder.fromMap(props.build(), Option.apply(IDEPathHelper.recorderConfigFile));
  }
}
