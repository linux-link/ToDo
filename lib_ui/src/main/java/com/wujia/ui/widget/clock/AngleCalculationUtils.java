package com.wujia.ui.widget.clock;

import static java.lang.Math.acos;

public class AngleCalculationUtils {

    /**
     * 操作之前的角度
     */
    float preDegree = 0F;

    /**
     * 当前角度
     */
    float curDegree = 0F;

    /**
     * 起始角度,startAngle<endAngle
     */
    float startAngle = 0F;

    /**
     * 结束角度,startAngle<endAngle
     */
    float endAngle = 360F;

    /**
     *  圆心坐标
     */
    float centerX = 0F;
    float centerY = 0F;

    /**
     * 计算当前角度
     */
    public void computeCurAngle(float x1, float y1, float x2, float y2) {
        curDegree = preDegree + calculateAngle(x1, y1, x2, y2);
    }

    /**
     * 重置操作之前的角度
     */
    private void resetPreDegree() {
        preDegree = curDegree;
    }


    /**
     * 计算两点的夹角
     */
    private float calculateAngle(float x1, float y1,float x2,float y2) {
        double angle1 = calculateAngle(x1, y1);
        double angle2 = calculateAngle(x2, y2);
        return (float) (angle2 - angle1);
    }

    /**
     * 计算坐标点与x轴的夹角
     */
    private double calculateAngle(float x,float y) {
        double distance = Math.sqrt(((x - centerX) * (x - centerX) + (y - centerY) * (y - centerY)));
        if (distance == 0F) {
            return 0F;
        }
        double degree = acos((x - centerX) / distance) * 180 / Math.PI;
        if (y < centerY) {
            degree = 360 - degree;
        }
        return degree;
    }
}
