package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class FireNStages extends SequentialCommandGroup {

  public FireNStages() {
    super(
      new ParallelRaceGroup(
        new LockOnTurret(),
        new SequentialCommandGroup(
          //new Until(G2),
          new SpinUpShooter(),
          //new Until(buttonpress),
          new ShootConveyor()
        )
      ),
      new ParallelCommandGroup(
        new TurretToZero(),
        new SpinDownShooter()
      )
    );
  }
}
