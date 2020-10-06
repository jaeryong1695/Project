


#ifndef _farm_sensor_H_
#define _farm_sensor_H_
#ifdef __cplusplus
extern "C" {
#endif

#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <sys/types.h>
#include <unistd.h>
#include <errno.h>
#include <wiringPi.h>

#define MAXTIMINGS 85

#define MOTION_IN 0  //gpio17
#define trigPin 1	//gpio 21
#define echoPin 29	//gpio 18
#define DCMOTOR 23 // BCM_GPIO 13
#define RGBLEDPOWER  24 //BCM_GPIO 19
#define RED	8
#define GREEN	7
#define BLUE	9
#define LIGHTCONTROL  28	//GPIO 4

extern int wiringPicheck();

extern int get_human_sensor(void);
extern int get_ultra_sensor(void);

extern int get_dcmotor_functionality();
extern int get_rgbled_functionality();
extern int get_light_functionality();

extern int act_dcmotor_on(const int _use);
extern int act_rgbled_on(const int _use);
extern int act_light_on(const int _use);

extern void lightOn();
extern void lightOff();
extern void rgbledOn();
extern void rgbledOff();

extern void dcmotorOn();
extern void dcmotorOff();


#ifdef __cplusplus
}
#endif
#endif

