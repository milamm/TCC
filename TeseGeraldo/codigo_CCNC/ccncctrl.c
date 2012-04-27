/*--------------PROGRAMA DE CONTROLE DO CCNC-SDCC----------------*/

#include	<p30f4013.h>// Biblioteca do dsPIC30F4013
#include	<uart.h>
#include	<timer.h>
#include	<dc12.h>
#include 	<ports.h>
#include 	<stdio.h>
#include 	<CloseUART1.c>
#include	<ConfigIntUART1.c>
#include	<OpenUART1.c>
#include	<ConfigINT1.c>
#include	<ConfigIntTimer1.c>
#include 	<OpenTimer3.c>
#include 	<ConfigIntTimer3.c>
#include	<OpenTimer1.c>
#include	<OpenTimer23.c>
/*-------Configurações internas do microcontrolador------------*/
_FOSC(CSW_FSCM_OFF & XT_PLL16);
_FWDT(WDT_OFF); 
_FBORPOR(MCLR_DIS & PBOR_OFF & PWRT_OFF);
_FGS(CODE_PROT_OFF); 
/*---------Variáveis Globais-----------------------------------*/
char it;
unsigned int ixr,jxr;
unsigned int buftemp;
unsigned long vetaux;
unsigned char idx;
float adf;
volatile unsigned int *ptr;
unsigned int ADResult1 = 0;
int data2;
float t_inf,settemp,eold,ierro;
char cmd, cmdflag,mandaTemp;
unsigned int oc1rs_var,iq,flag_test;
float centroid,erro,derro,eold,ierro,vts[3],vti[3],P,D,I,PDI;
float t_sup,erro,t_erro;
/*----------Definição das constantes---------------------*/
#define		LED_RD0				PORTDbits.RD0 //VALVULAS
#define		LED_RD1				PORTDbits.RD1 //BOMBA
#define		LED_RD2				PORTDbits.RD2
#define		LED_RD3				PORTDbits.RD3
#define		ONE_WIRE_PIN	   	PORTBbits.RB4  //SENSOR DE t_sup1
#define 	ONE_WIRE_PIN_OUT   	TRISBbits.TRISB4=0;
#define 	ONE_WIRE_PIN_IN    	TRISBbits.TRISB4=1;
#define 	ONE_WIRE_PIN_FLOAT 	TRISBbits.TRISB4=1;
#define		ONE_WIRE_PIN2	    PORTBbits.RB5   //SENSOR DE t_sup2
#define 	ONE_WIRE_PIN_OUT2   TRISBbits.TRISB5=0;
#define 	ONE_WIRE_PIN_IN2    TRISBbits.TRISB5=1;
#define 	ONE_WIRE_PIN_FLOAT2 TRISBbits.TRISB5=1;
#define     FREQ_AQ 			31500
#define 	TRUE 				1
#define 	FALSE 				0
/*-------------DEFINIÇÃO das FUNÇÕES----------------*/
void onewire_reset();
float ds1820_read(); 
void onewire_write(int data) ;
int onewire_read() ;
void delay_ms(unsigned int tempo);
void delay_us(unsigned int tempo);
void inicializa(void);
void __attribute__ ((__interrupt__)) _T1Interrupt(void);
void __attribute__ ((__interrupt__)) _T3Interrupt(void);
void __attribute__((__interrupt__)) _ADCInterrupt(void);
void _ISR _U1TXInterrupt(void);
void _ISR _U1RXInterrupt(void);
void __attribute__ ((__interrupt__)) _INT0Interrupt(void);
void inicia_UART();
void cmd_A();
void cmd_SetTemp();
void cmd_LigaValvula();
void cmd_DesligaValvula();
void cmd_LigaBomba();
void cmd_DesligaBomba();
/*-------------------------------------------------------------*/
float ds1820_read() 
{ 
	int busy=0, temp1, temp2, temp1B, temp2B; 
	int temp3,temp22,temp3B,temp22B; 
	float result; 
	onewire_reset(); 
	onewire_write(0xCC); 
	onewire_write(0x44); 
	while (busy == 0) busy = onewire_read(); 
	onewire_reset(); 
	onewire_write(0xCC); 
	onewire_write(0xBE); 
	temp1 = onewire_read()&0xff; temp1B=data2;
	temp2 = onewire_read()&0xff; temp2B=data2;
	temp22 =(temp2<<8);			 temp22B =(temp2B<<8);
	temp3=temp22+temp1 ; 		 temp3B=temp22B+temp1B;
	result = (float) temp3/16.0; //Calculo DS18B20 com 0.1C de resolução
	t_inf = (float) temp3B/16.0;
	return(result); 
} 
/*-------------------------------------------------------------*/
void onewire_reset() 
{ 
	ONE_WIRE_PIN=1;	
	ONE_WIRE_PIN_OUT;
    ONE_WIRE_PIN=0; 
	delay_us( 250 ); 
	ONE_WIRE_PIN_FLOAT;  
	delay_us( 250 );
 
	ONE_WIRE_PIN2=1;
   	ONE_WIRE_PIN_OUT2;
	ONE_WIRE_PIN2=0; 
	delay_us( 250 ); 
  	ONE_WIRE_PIN_FLOAT2;
	delay_us( 250 ); 
} 
/*********************** onewire_write() ********************************/ 
void onewire_write(int data) 
{ 
	char count; 
	ONE_WIRE_PIN=1; 	ONE_WIRE_PIN2=1; 
 	for (count=0; count<8; ++count) 
 	{ 
	   ONE_WIRE_PIN_OUT;  ONE_WIRE_PIN_OUT2;
	   ONE_WIRE_PIN=0; ONE_WIRE_PIN2=0; 
	   delay_us( 2 ); 
	   ONE_WIRE_PIN=data; ONE_WIRE_PIN2=data; 
   	   data=data>>1;
  	   delay_us( 60 );
	   ONE_WIRE_PIN_FLOAT; ONE_WIRE_PIN_FLOAT2;
	   delay_us( 2 ); 
	 } 
} 
/*********************************************************************/ 
int onewire_read() 
{ 
 	int count, data,c,c2; 
 	data=0; data2=0;
 	for (count=0; count<8; count++) 
 	{ 
    	ONE_WIRE_PIN=1;    		ONE_WIRE_PIN2=1; 
		ONE_WIRE_PIN_OUT; 		ONE_WIRE_PIN_OUT2;
		ONE_WIRE_PIN=0;  		ONE_WIRE_PIN2=0; 
    	delay_us( 2 ); 
		ONE_WIRE_PIN_FLOAT; 	ONE_WIRE_PIN_FLOAT2; 
    	delay_us( 8 ); 
		c=(ONE_WIRE_PIN& 0x01);	c2=(ONE_WIRE_PIN2& 0x01);
		data=data|(c<<(count));	data2=data2|(c2<<(count));
    	delay_us( 120 ); 
 	} 
 	return( data ); 
} 
/*-------------------------------------------------------------*/
void __attribute__ ((__interrupt__)) _T1Interrupt(void)
{
	IFS0bits.T1IF=0;	//Limpa bit de interrupção de timer1
}
/*-------------------------------------------------------------*/
void __attribute__ ((__interrupt__)) _T3Interrupt(void)
{
}
/*-------------------------------------------------------------*/
void _ISR _U1TXInterrupt(void)
{
   _U1TXIF = 0; //limpa o flag da interrupção da transmissão de dados pela UART
}
/*-------------------------------------------------------------*/
/*-Rotina de tratamento da Interrupção da recebimento de dados-*/
void _ISR _U1RXInterrupt(void)
{
   _U1RXIF = 0;//limpa o flag da interrupção
    cmdflag=1;
    if(U1MODEbits.PDSEL == 3) cmd = U1RXREG;
	else cmd = U1RXREG & 0xFF;
}
/*-------------------------------------------------------------*/
void __attribute__((__interrupt__)) _ADCInterrupt(void)
{
}
/*-------------------------------------------------------------*/
void __attribute__ ((__interrupt__)) _INT1Interrupt(void)
{
	IFS1bits.INT1IF=0;		//Limpa flag de interrupção externa 1	
}
/*-------------------Inicialização da UART---------------------*/
void inicia_UART()
{
   unsigned int ubrg;                  
   unsigned int config1;               
   unsigned int config2;               
   it=0;
   CloseUART1();//desabilita UART1 
   ubrg=34;
   ConfigIntUART1(UART_RX_INT_EN &  // habilita a interrupção de recebimento da uart
               UART_RX_INT_PR6 & //define a prioridade da interrupção de recebimento como 6
               UART_TX_INT_EN &  //Habilita interrupção de transmissão da uart
               UART_TX_INT_PR2); //define a prioridade da interrupção da transmissão como 2
   config1    =    UART_EN &  //habilita uart1
               UART_IDLE_CON & //uart funciona no modo IDLE
               UART_RX_TX & //configura a operação nos pinos padrões rx tx
               UART_DIS_WAKE & //desabilita o "wake-up" qdo inicia a UART
               UART_DIS_LOOPBACK & //Desabilita o modo loop back
               UART_DIS_ABAUD &//desabilita o modo autobaud
               UART_NO_PAR_8BIT & // define dados com 8bits e sem bit de paridade
               UART_1STOPBIT;//define o stopbit como 1
   config2    =    UART_INT_TX_BUF_EMPTY &//interrupção tx qdo o buffer estiver vazio
               UART_TX_PIN_NORMAL &//define o bit tx break como normal
               UART_TX_ENABLE &// habilita a transmissão da uart
			   UART_INT_RX_CHAR &   //* Interrupt on every char received */
               UART_ADR_DETECT_DIS & //desabilita o modo addres detect
               UART_RX_OVERRUN_CLEAR;// limpa o flag de overrun
   OpenUART1(config1, config2, ubrg);//configura a UART1
}
/*----------------------Rotina Principal---------------------*/
void inicializa(void)
{
	ADPCFG=0b1111111111111110; 				 //Configura o RB0 como entrada analógica
	ADCON1 = 0x00E0;
	ADCHS = 0x0000; 
	ADCSSL = 0;
	ADCON3 = 0xff7d;
	ADCON2 = 0x0000;
	ADCON1bits.ADON = 1;
   	idet=0;
   	OC1CON=0b0000000000000110;	
   	OC1R=0;	
   	OC1RS=0;										//Inicializa registrador
   	PR2=0xFFFF;
   	T2CON=0b1000000000000000;
   	TRISBbits.TRISB0=1;		//Configura o pino RB0 como entrada.
   	TRISBbits.TRISB1=1;		//Configura o pino RB1 como entrada.
   	TRISBbits.TRISB2=1;		//Configura o pino RB1 como entrada.
	TRISDbits.TRISD0=0;		//Configura o pino RD0 como saída para o LED
    TRISDbits.TRISD1=0;		//Configura o pino RD1 como saída para o LED
    TRISDbits.TRISD2=0;		//Configura o pino RD2 como saída para o LED
   	OpenTimer3(T3_ON & T3_IDLE_STOP & T3_GATE_OFF & T3_PS_1_1 &  T3_SOURCE_INT,FREQ_AQ); 
	ixr=0;
	cmdflag=0;
    LED_RD2=0;
    LED_RD3=0;
	mandaTemp=TRUE;
}
/*-------------------------------------------------------------*/
void delay_ms(unsigned int tempo)
{
	unsigned int tempo1;	
	if (!tempo) return;	//Se tempo for igual a zero retorna da função	
	for (tempo1=1;tempo1<=tempo;tempo1++) delay_us(1000); //Aguarda n 1000 us
}
/*-------------------------------------------------------------*/
void delay_us(unsigned int tempo)
{
	if (!tempo) return; //Se tempo for igual a zero retorna da função	
	do
	{
		Nop();Nop();Nop();Nop();Nop();Nop();Nop();Nop();Nop();
		Nop();Nop();Nop();Nop();Nop();Nop();Nop();Nop();Nop();
		Nop();Nop();Nop();Nop();Nop();Nop();Nop();
		tempo--;
	}while(tempo);
}
/********************************************************************************/
void cmd_SetTemp()
{
	unsigned char lsb,msb;
	printf("SETTEMP");
    cmdflag=0;
    while(cmdflag==0);
	cmdflag=0;
	lsb=cmd;
    while(cmdflag==0);
	cmdflag=0;
	msb=cmd;
	settemp=((msb*256)+lsb)/1000.0;
    printf("%5.3f  %u  %u\n\r",settemp,msb,lsb);
}
/********************************************************************************/
void cmd_A()
{
	unsigned char lsb,msb;
	putchar('C'); putchar('M');	putchar('D');
   	putchar('1');
    cmdflag=0;
    while(cmdflag==0);
	cmdflag=0;
	lsb=cmd;
    while(cmdflag==0);
	cmdflag=0;
	msb=cmd;
	putchar(msb);
	putchar(lsb);
    printf("\n\r");
	oc1rs_var=(msb*256)+lsb;
    printf("%u  %u  %u\n\r",oc1rs_var,msb,lsb);
}
/************************************************************/
void cmd_LigaValvula()
{
	LED_RD2=1;
}
/************************************************************/
void cmd_DesligaValvula()
{
	LED_RD2=0;  //DESLIGA BOMBA
	ConfigIntTimer3(T3_INT_PRIOR_6 & T3_INT_ON); //habilita interrupção do timer 3
}
/************************************************************/
void cmd_LigaBomba()
{
	LED_RD3=1; //Liga Bomba
}
/************************************************************/
void cmd_DesligaBomba()
{
	LED_RD3=0;//Desliga Bomba
}
/*************************************************************/
void cmd_Baixa_temp()
{
  flag_test=1;
}
/*************************************************************/
void cmd_SobeTemp()
{
  flag_test=2;
}
/*************************************************************/
void cmd_DesligaPeltier()
{
  flag_test=3;
}
/*************************************************************/
void cmd_ModoNormal()
{
  flag_test=0;
}
/*************************************************************/
int main(void)
{
	TRISD = 0x00;    //Saídas
	oc1rs_var=0xffff/2;
	OC1RS=oc1rs_var;
	settemp=0;
	inicializa();							//Chama rotina de inicialização de interrupção
	inicia_UART();   //inicializa a UART               
   	t_sup=ds1820_read();
    erro=(settemp-(t_sup-t_inf));
    eold=erro;
	printf("\n\r -->CCNC NOVO<-- \n\r");
	ierro=0.0;
	while(1) //loop infinito
   {
	  	t_sup=ds1820_read();
		if((t_sup>50)||(t_inf>50)) cmd_DesligaPeltier();
	  	eold=erro;
      	erro=(t_sup-t_inf)-settemp;
      	ierro=ierro+(erro);
	  	if(ierro>15000) ierro=15000;
	  	if(ierro<-15000) ierro=-15000;
      	derro=erro-eold;
		P=0.15*erro;
    	D=0.0;
    	I=0.001*ierro; //era 0.003
    	PDI=(P+I+D)+0.5;
   		if(PDI>1.0) PDI=1.0;
   		if(PDI<0) PDI=0.0;
      	centroid=PDI; //*I+derro*D;
     	if(centroid>1) centroid=1;
    	if(centroid<0) centroid=0;
     	if(flag_test==0)OC1RS=(0xffff*centroid);
     	if(flag_test==1)OC1RS=0xffff;
     	if(flag_test==2)OC1RS=0x0;
     	if(flag_test==3) OC1RS=0x7fff;

		if(mandaTemp==TRUE)printf("TSTI %5.3f %5.3f\n\r",t_sup, t_inf);
	  	if (cmdflag==1)  
	  	{
			cmdflag=0;
			switch (cmd)
        	{
				case 0x41: //A
					cmd_A();
					break;
				case 0x42:
					cmd_SetTemp(); //B
					break;
				case 0x43:
					cmd_LigaValvula(); //C
					break;
				case 0x44:
					cmd_DesligaValvula(); //D
					break;
				case 0x45:
					cmd_LigaBomba(); //E
					break;
				case 0x46:
					cmd_DesligaBomba(); //F
					break;
				case 0x47:
					cmd_SobeTemp(); //H
					break;
				case 0x48:
					cmd_Baixa_temp(); //G
					break;
				case 0x49:
					cmd_DesligaPeltier(); //I
					break;
				case 0x4a:
					cmd_ModoNormal();
					break;
				case 0x4b:
					cmd_MandaVetorFotoDetector(); //K
					break;
				case 0x4c:
					mandaTemp=FALSE; //L  Desabilita o envio das temperaturas
					break;
				case 0x4d:
					mandaTemp=TRUE; //M  Habilita o envio das temperaturas
					break;
		}
	  }
   }
   return 0;
}
