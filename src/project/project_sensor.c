

#include "farm_sensor.h"


int wiringPicheck(void)
{
	if (wiringPiSetup() == -1)
	{
		fprintf(stdout, "Unable to start wiringPi: %s\n", strerror(errno));
		return 1;
	}
}

int get_human_sensor()
{
	return 0;
}
int get_ultra_sensor()
{
	return 0;
}
void rgbledOn()
{
	pinMode(RED, OUTPUT);
	pinMode(GREEN, OUTPUT);
	pinMode(BLUE, OUTPUT);

	printf("rgbled() \n");

	digitalWrite(RED, 1);
}

void rgbledOff()
{
	pinMode(RED, OUTPUT);
	pinMode(GREEN, OUTPUT);
	pinMode(BLUE, OUTPUT);

	digitalWrite(RED, 0);
	digitalWrite(BLUE, 0);
	digitalWrite(GREEN, 0);
}
void lightOn()
{
	pinMode(LIGHTCONTROL, OUTPUT);
	digitalWrite(LIGHTCONTROL, 1);
}
void lightOff()
{
	pinMode(LIGHTCONTROL, OUTPUT);
	digitalWrite(LIGHTCONTROL, 0);
}

void dcmotorOn()
{
	pinMode(DCMOTOR, OUTPUT);
	printf("Motor ON \n");
	digitalWrite(DCMOTOR, 1);
}

void dcmotorOff()
{
	pinMode(DCMOTOR, OUTPUT);
	printf("Motor OFF \n");
	digitalWrite(DCMOTOR, 0);
}
int get_dcmotor_functionality()
{
	return 1;
}
int get_rgbled_functionality()
{
	return 1;
}
int get_light_functionality()
{
	return 1;
}

int set_dcmotor_active(const int _use)
{
	if (wiringPicheck())
		printf("%s Fail (param :%d) \n", _use);

	pinMode(DCMOTOR, OUTPUT);
	digitalWrite(DCMOTOR, _use);
}
int set_rgbled_active(const int _use)
{
	if (wiringPicheck())
		printf("%s Fail (param :%d) \n", _use);
	
	pinMode(RGBLEDPOWER, OUTPUT);
	pinMode(RED, OUTPUT);
	pinMode(GREEN, OUTPUT);
	pinMode(BLUE, OUTPUT);

	pinMode(RGBLEDPOWER, OUTPUT);
	digitalWrite(RED, _use);
}
int set_light_active(const int _use)
{
	if (wiringPicheck())
		printf("%s Fail (param :%d) \n", _use);

	pinMode(LIGHTCONTROL, OUTPUT);
	digitalWrite(LIGHTCONTROL, _use);
}




