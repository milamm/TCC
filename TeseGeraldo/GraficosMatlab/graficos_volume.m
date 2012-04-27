%Graficos Variação da concentração

close all
clear all
load volume

xSize = 8; ySize = 12;
xLeft = (21-xSize)/2; yTop = (30-ySize)/2;

figure1 = figure('Color',[1 1 1]);

axes1=axes('Parent',figure1,'YTick',[0 10 100 1000 15000],...
    'YScale','log',...
    'XTick',[0 193 266  480],...
    'FontSize',12,...
    'XGrid','on',...
    'FontName','times new roman');
% Uncomment the following line to preserve the X-limits of the axes
 xlim([0 480]);
% Uncomment the following line to preserve the Y-limits of the axes
 ylim([0 20000]);
% Uncomment the following line to preserve the Z-limits of the axes
% zlim([-1 1]);
box('on');
hold('all');

% Create plot
%plot(t,ccnc,'Marker','.','LineStyle','none');
plot(h,p,'k-');

% Create xlabel
xlabel('Altura da imagem (pixels) y','FontSize',12,'FontName','times new roman');

% Create ylabel
ylabel('Número de pixels brancos i(y)','FontSize',12,'FontName','times new roman');

% Create textbox
annotation(figure1,'textbox',[0.4421 0.1119 0.1276 0.05952],...
    'String',{'73 pixels'},...
    'FitBoxToText','off',...
    'LineStyle','none');

stop
% Create plot
plot(X2,X2,'LineStyle',':');

% 
% set(plot1(1),'Color',[1 0 0],'DisplayName','CPC Médio');
% set(plot1(2),'Color',[0 1 0],'DisplayName','CPC Max');
% set(plot1(3),'Color',[0 0 1],'DisplayName','CPC Min');


