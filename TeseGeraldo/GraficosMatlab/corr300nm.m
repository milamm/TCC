%Graficos de correla��o

close all
clear all
load corr100nm

figure1 = figure('Color',[1 1 1]);

axes('Parent',figure1,'YTick',[0 1000 2e+3 3e+3 4e+3 5e+3],...
    'XTick',[0 1000 2e+3 3e+3 4e+3 5e+3],...
    'FontSize',12,...
    'FontName','times new roman');
% Uncomment the following line to preserve the X-limits of the axes
xlim([0 5000]);
% Uncomment the following line to preserve the Y-limits of the axes
 ylim([0 5000]);
% Uncomment the following line to preserve the Z-limits of the axes
% zlim([-1 1]);
box('on');
hold('all');

% Create plot
%plot(t,ccnc,'Marker','.','LineStyle','none');
plot(x,y,'k.');

% Create xlabel
xlabel('(part�culas/cm^3) CPC','FontSize',12,'FontName','times new roman');

% Create ylabel
ylabel('(part�culas/cm^3) CCNC-SDCC','FontSize',12,'FontName','times new roman');
text(200,4000,'R^2=0,99','FontSize',12,'FontName','times new roman');

x1=0:5000;
y1=0:5000;
hold on
plot(x1,y1);