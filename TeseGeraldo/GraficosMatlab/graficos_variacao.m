%Graficos Variação da concentração

close all
clear all
load variacao
cpc=cpc/1000;

xSize = 8; ySize = 12;
xLeft = (21-xSize)/2; yTop = (30-ySize)/2;

figure1 = figure('Color',[1 1 1]);

axes1=axes('Parent',figure1,'YTick',[0 1 2 3],...
    'XTick',[0  50  100  150  200],...
    'FontSize',12,...
    'FontName','times new roman');
% Uncomment the following line to preserve the X-limits of the axes
 xlim([0 200]);
% Uncomment the following line to preserve the Y-limits of the axes
 ylim([0 3.5]);
% Uncomment the following line to preserve the Z-limits of the axes
% zlim([-1 1]);
box('on');
hold('all');

% Create plot
%plot(t,ccnc,'Marker','.','LineStyle','none');
plot(t,cpc,'k-');

% Create xlabel
xlabel('t (minutos)','FontSize',12,'FontName','times new roman');

% Create ylabel
ylabel('Partículas/cm^3','FontSize',12,'FontName','times new roman');
text(200,4000,'R^2=0,99','FontSize',12,'FontName','times new roman');

% legend1 = legend(axes1,'show');
% set(legend1,'Location','SouthEast');
annotation(figure1,'textbox',[0.1346 0.9123 0.05708 0.07828],...
    'String',{'x10^3'},...
    'FontSize',12,...
    'FontName','times new roman',...
    'LineStyle','none');
set(axes1,'YGrid','on')


stop
% Create plot
plot(X2,X2,'LineStyle',':');

% 
% set(plot1(1),'Color',[1 0 0],'DisplayName','CPC Médio');
% set(plot1(2),'Color',[0 1 0],'DisplayName','CPC Max');
% set(plot1(3),'Color',[0 0 1],'DisplayName','CPC Min');


