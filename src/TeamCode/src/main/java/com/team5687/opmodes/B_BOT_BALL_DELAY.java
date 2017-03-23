package com.team5687.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.team5687.Constants;
import com.team5687.helpers.GeneralHelpers;
import com.team5687.helpers.Logger;
import com.team5687.primitives.Motor;

/**
 * Created by stephen on 4/12/16.
 */
@Disabled
@Autonomous(name = "B-BOT-BALL-DELAY", group = "Test")
public class B_BOT_BALL_DELAY extends OpMode {


    Motor _left;
    Motor _right;
    int _done =0;

    @Override
    public void init() {
        Logger.getInstance().SetTelemetry(telemetry);
        _right = new Motor(DcMotorSimple.Direction.REVERSE, hardwareMap.dcMotor.get(Constants.LEFT_DRIVE_MOTOR), true);
        _left = new Motor(DcMotorSimple.Direction.REVERSE, hardwareMap.dcMotor.get(Constants.RIGHT_DRIVE_MOTOR), true);

        _right.SetEncoderDirection(DcMotorSimple.Direction.REVERSE);
        _left.SetEncoderDirection(DcMotorSimple.Direction.FORWARD);

        _left.Stop();
        _right.Stop();

        String message = String.format("Left Motor: %s Right Motor: %s", _left.IsBusy() ? "Busy" : "Not Busy", _right.IsBusy() ? "Busy" : "Not Busy");
        Logger.getInstance().WriteMessage(message);
    }

    @Override
    public void loop() {
        int counter =0;
        int inches = 68;
        int targetPower = 3600;

        if(!_left.IsBusy() && !_right.IsBusy() && _done == 0 && counter < 300) {
            _left.Stop();
            _right.Stop();
            counter++;
            _done = 1;

        }

        if(!_left.IsBusy() && !_right.IsBusy() && _done == 1) {
            _left.SetTargetEncoderPosition(targetPower, GeneralHelpers.CalculateDistanceEncode(inches));
            _right.SetTargetEncoderPosition(targetPower, GeneralHelpers.CalculateDistanceEncode(inches));
            _left.SetEncoderMode(DcMotor.RunMode.RUN_TO_POSITION);
            _right.SetEncoderMode(DcMotor.RunMode.RUN_TO_POSITION);
            _done = 2;
        }


        /*if(_left.GetEncoderPosition() < _right.GetEncoderPosition() && _left.GetEncoderPosition() < _left.GetTargetEncoderPosition()) {
            _left.SetSpeed(targetPower + 10);
            _right.SetSpeed(targetPower);
        }
        else if(_right.GetEncoderPosition() < _left.GetEncoderPosition() && _right.GetEncoderPosition() < _right.GetTargetEncoderPosition()) {
            _left.SetSpeed(targetPower);
            _right.SetSpeed(targetPower + 10);
        }
        else if(_left.GetEncoderPosition() == _right.GetEncoderPosition() && _left.GetEncoderPosition() < _left.GetTargetEncoderPosition()) {
            _left.SetSpeed(targetPower);
            _right.SetSpeed(targetPower);
        }

        if(_left.GetEncoderPosition() >= _left.GetTargetEncoderPosition()) {
            _left.SetSpeed(0);
            _right.Stop();
        }

        if(_right.GetEncoderPosition() >= _right.GetTargetEncoderPosition()) {
            _right.SetSpeed(0);
            _right.Stop();
        }*/

        /*if(_left.IsBusy())
            _left.Stop();
        if(_right.IsBusy())
            _right.Stop();*/


        String message = String.format("L: %d %d (%s) R: %d %d(%s) T: %d",
                _left.Motor().getTargetPosition(),
                _left.Motor().getCurrentPosition(),
                _left.IsBusy() ? "Busy" : "Not Busy",
                _right.Motor().getTargetPosition(),
                _right.Motor().getCurrentPosition(),
                _right.IsBusy() ? "Busy" : "Not Busy",
                (int)GeneralHelpers.CalculateDistanceEncode(inches));
        Logger.getInstance().WriteMessage(message);
    }
}
