package frc.robot;

import java.io.IOException;
import java.nio.file.Path;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.AdvancedClimb;
import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.ChaseBall;
import frc.robot.commands.SimpleClimb;
import frc.robot.commands.ConveyorIntake;
import frc.robot.commands.FarShootAndDrive;
import frc.robot.commands.Harvest;
import frc.robot.commands.ManualConveyor;
import frc.robot.commands.ManualHarvest;
import frc.robot.commands.ManualShooter;
import frc.robot.commands.NeutralClimb;
import frc.robot.commands.NeutralHarvest;
import frc.robot.commands.NeutralShooter;
import frc.robot.commands.NeutralTurret;
import frc.robot.commands.OscillateTurret;
import frc.robot.commands.SimpleShoot;
import frc.robot.commands.Sow;
import frc.robot.commands.SpinDownShooter;
import frc.robot.commands.SpinUpShooter;
import frc.robot.commands.ThreeSecondDriveAndShoot;
import frc.robot.commands.TurretToZero;
import frc.robot.commands.TwoSecondDrive;
import frc.robot.constants.DrivetrainConstants;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Conveyor;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Harvester;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Turret;

public class RobotContainer {
  private Compressor m_Compressor;

  private Climber m_Climber;
  private Controller m_Controller;
  private Conveyor m_Conveyor;
  private Drivetrain m_Drivetrain;
  private Harvester m_Harvester;
  private Shooter m_Shooter;
  private Turret m_Turret;
  private VisionClient m_VisionClient;

  private SendableChooser<String> m_autonChooser;
  private static final String auto1 = "auto1";
  private static final String auto2 = "auto2";
  private static final String auto3 = "auto3";


  public RobotContainer() {
    m_Compressor = new Compressor();
    m_Compressor.start();

    m_Climber = Climber.getInstance();
    m_Controller = Controller.getInstance();
    m_Conveyor = Conveyor.getInstance();
    m_Drivetrain = Drivetrain.getInstance();
    m_Harvester = Harvester.getInstance();
    m_Shooter = Shooter.getInstance();
    m_Turret = Turret.getInstance();
    m_VisionClient = VisionClient.getInstance();

    m_autonChooser = new SendableChooser<>();
    m_autonChooser.addOption("2 Second Drive", auto1);
    m_autonChooser.addOption("3 Second Drive And Shoot", auto2);
    m_autonChooser.addOption("Far Shoot and Drive", auto3);
    SmartDashboard.putData("Auton Chooser", m_autonChooser);

    m_Climber.setDefaultCommand(new NeutralClimb());
    m_Conveyor.setDefaultCommand(new ConveyorIntake());
    m_Drivetrain.setDefaultCommand(new ArcadeDrive(() -> m_Controller.getArcadeDriveSpeed(), () -> m_Controller.getArcadeDriveRot(), () -> m_Controller.shift()));
    m_Harvester.setDefaultCommand(new NeutralHarvest());
    m_Shooter.setDefaultCommand(new NeutralShooter());
    m_Turret.setDefaultCommand(new NeutralTurret());

    configureButtonBindings();
  }

  private void configureButtonBindings() {
    //High Level
    m_Controller.getHarvestButton().whenHeld(new Harvest());
    // m_Controller.getBallChaseButton().whenHeld(new ChaseBall());
    m_Controller.getShootButton().whenHeld(new SimpleShoot(() -> m_Controller.getConveyorShootUp(), () -> m_Controller.getConveyorShootDown()));

    //Low Level
    m_Controller.getManualHarvestButton().whenHeld(new ManualHarvest(() -> m_Controller.getManualHarvestMotorSupply(), () -> m_Controller.getManualHarvestActuate()));
    m_Controller.getManualConveyorButton().whenHeld(new ManualConveyor(() -> m_Controller.getManualConveyorSupplier()));
    m_Controller.getClimbButton().whenHeld(new AdvancedClimb(() -> m_Controller.getClimbPercent()));
    m_Controller.getSowButton().whenHeld(new Sow());
    // m_Controller.getManualShooterButton().whenHeld(new ManualShooter(() -> m_Controller.getManualShooterFlywheel(), () -> m_Controller.getManualShooterBooster()));
    //m_Controller.getManualTurretButton().whenHeld(new ManualTurret(() -> m_Controller.getManualTurretIncrease()));
    m_Controller.getSpinDownButton().whenPressed(new SpinDownShooter());
    m_Controller.getSpinUpButton().whenPressed(new SpinUpShooter());
  }

  public Command getAutonomousCommand() {
    if(m_autonChooser.getSelected().equals("auto1")) {
      return new TwoSecondDrive();
    } else if(m_autonChooser.getSelected().equals("auto2")) {
      return new ThreeSecondDriveAndShoot();
    } else if(m_autonChooser.getSelected().equals("auto3")) {
      return new FarShootAndDrive();
    }
    return new TwoSecondDrive();
  }
}
