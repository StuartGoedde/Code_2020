package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class FireBlind extends SequentialCommandGroup {

  public FireBlind() {
    super(
      new SequentialCommandGroup( 
        new SpinUpShooter(),
        new ShootConveyor(),
        new SpinDownShooter()
      )
    );
  }
}
