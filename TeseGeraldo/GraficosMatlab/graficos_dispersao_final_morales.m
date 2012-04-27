%Graficos sba
% Dispersão CPC X CCNC
% 26/01/2011
%--------------------------------------------------------------------------
close all
clear all

load dispersao_dados

xSize = 8; ySize = 12;
xLeft = (21-xSize)/2; yTop = (30-ySize)/2;

 figure1 = figure('Color',[1 1 1]);%'PaperUnits','centimeters',...
%     'PaperPosition',[xLeft yTop xSize ySize],...
%     'Position',[1 1 xSize*50 ySize*50],...
%     ;



%  'PaperType','custom',...    
%    'PaperSize',[6 6],...
 %   'PaperPositionMode','manual',...    
 %'InvertHardcopy','off',...
  %   'Units','centimeters',...
% Create axes
axes1=axes('Parent',figure1,'YTick',[0 500 1000 1500 2000 2500 3000],...
    'XTick',[0 25 50 75 100 125 150 175 200],...
    'FontSize',12,...
    'FontName','times new roman');
% Uncomment the following line to preserve the X-limits of the axes
 xlim([0 200]);
% Uncomment the following line to preserve the Y-limits of the axes
 ylim([0 3000]);
% Uncomment the following line to preserve the Z-limits of the axes
% zlim([-1 1]);
box('on');
hold('all');

% Create plot
%plot(t,ccnc,'Marker','.','LineStyle','none');
plot1=plot(tccnc,ccnc,'k.');
plot2=plot(tcpc,cpc,'r+');





% Create xlabel
xlabel('t (minutos)','FontSize',12,'FontName','times new roman');

% Create ylabel
ylabel('Partículas/cm^3','FontSize',12,'FontName','times new roman');
text(200,4000,'R^2=0,99','FontSize',12,'FontName','times new roman');

legend1 = legend(axes1,'show');
set(legend1,'Location','SouthEast');


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Get xdata from plot
xdata1 = get(plot1, 'xdata');
% Get ydata from plot
ydata1 = get(plot1, 'ydata');
% Make sure data are column vectors
xdata1 = xdata1(:);
ydata1 = ydata1(:);

% Remove NaN values and warn
nanMask1 = isnan(xdata1(:)) | isnan(ydata1(:));
if any(nanMask1)
    warning('GenerateMFile:IgnoringNaNs', ...
        'Data points with NaN coordinates will be ignored.');
    xdata1(nanMask1) = [];
    ydata1(nanMask1) = [];
end
% Find x values for plotting the fit based on xlim
axesLimits1 = xlim(axes1);
xplot1 = linspace(axesLimits1(1), axesLimits1(2));

% Find coefficients for polynomial (order = 1)
fitResults1 = polyfit(xdata1, ydata1, 1);
% Evaluate polynomial
yplot1 = polyval(fitResults1, xplot1);
% Plot the fit
fitLine1 = plot(xplot1,yplot1,'DisplayName','   linear','Parent',axes1,...
    'Tag','linear',...
    'Color',[0 0 0]);

% Set new line in proper position
%setLineOrder(axes1, fitLine1, plot1);

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

% Get xdata from plot
xdata2 = get(plot2, 'xdata');
% Get ydata from plot
ydata2 = get(plot2, 'ydata');
% Make sure data are column vectors
xdata2 = xdata2(:);
ydata2 = ydata2(:);

% Remove NaN values and warn
nanMask2 = isnan(xdata2(:)) | isnan(ydata2(:));
if any(nanMask2)
    warning('GenerateMFile:IgnoringNaNs', ...
        'Data points with NaN coordinates will be ignored.');
    xdata2(nanMask2) = [];
    ydata2(nanMask2) = [];
end
% Find x values for plotting the fit based on xlim
axesLimits2 = xlim(axes1);
xplot2 = linspace(axesLimits2(1), axesLimits2(2));

% Find coefficients for polynomial (order = 1)
fitResults2 = polyfit(xdata2, ydata2, 1);
% Evaluate polynomial
yplot2 = polyval(fitResults2, xplot2);
% Plot the fit
fitLine2 = plot(xplot2,yplot2,'DisplayName','   linear','Parent',axes1,...
    'Tag','linear',...
    'Color',[1 0 0]);

% Set new line in proper position
setLineOrder(axes1, fitLine2, plot2);




%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

stop
% Create plot
plot(X2,X2,'LineStyle',':');

% 
% set(plot1(1),'Color',[1 0 0],'DisplayName','CPC Médio');
% set(plot1(2),'Color',[0 1 0],'DisplayName','CPC Max');
% set(plot1(3),'Color',[0 0 1],'DisplayName','CPC Min');



plt_ccnc=1.6346*tccnc+1574.5;
plt_cpc= 1.4499*tcpc+1560.3;

hd_ccnc=ccnc-plt_ccnc;
hd_cpc=cpc-plt_cpc;

figure(1)
hist(hd_ccnc(1:100,1),8); figure(gcf)
xlim([-2000 2000]);
ylim([0 40]);
xlabel('Amplitude do desvio')
ylabel('Número de ocorrências');

figure(2)
hist(hd_cpc(1:100,1),8); figure(gcf)
xlim([-2000 2000]);
ylim([0 40]);
xlabel('Amplitude do desvio')
ylabel('Número de ocorrências');





