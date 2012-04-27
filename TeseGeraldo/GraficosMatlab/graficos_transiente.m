%Graficos Variação da concentração

close all
clear all
load transiente

xSize = 8; ySize = 12;
xLeft = (21-xSize)/2; yTop = (30-ySize)/2;

figure1 = figure('Color',[1 1 1]);

axes1=axes('Parent',figure1,'YTick',[0 5000 10000 15000 20000],...
    'XTick',[0 1 2 3 4 5 6],...
    'FontSize',12,...
    'FontName','times new roman');
% Uncomment the following line to preserve the X-limits of the axes
 xlim([0 6]);
% Uncomment the following line to preserve the Y-limits of the axes
 ylim([0 20000]);
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
%text(200,4000,'R^2=0,99','FontSize',12,'FontName','times new roman');

% Create rectangle
annotation(figure1,'rectangle',[0.3921 0.369 0.08829 0.1643],...
    'FaceColor','flat',...
    'Color',[1 0 0]);

% Create rectangle
annotation(figure1,'rectangle',[0.4814 0.269 0.1829 0.5881],...
    'FaceColor','flat',...
    'Color',[1 0 0]);

% Create textbox
annotation(figure1,'textbox',[0.4071 0.4609 0.05179 0.06667],...
    'String',{'(a)'},...
    'FitBoxToText','off',...
    'LineStyle','none');

% Create textbox
annotation(figure1,'textbox',[0.5732 0.7085 0.05179 0.06667],...
    'String',{'(b)'},...
    'FitBoxToText','off',...
    'LineStyle','none');


stop
% Create plot
plot(X2,X2,'LineStyle',':');

% 
% set(plot1(1),'Color',[1 0 0],'DisplayName','CPC Médio');
% set(plot1(2),'Color',[0 1 0],'DisplayName','CPC Max');
% set(plot1(3),'Color',[0 0 1],'DisplayName','CPC Min');


