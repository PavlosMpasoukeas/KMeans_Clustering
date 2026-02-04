M = 13;
centers_file = sprintf("kmeans_best_centers_Μ%d.csv", M);

data = dlmread("dataset_O.csv", ",", 1, 0);
x1 = data(:,1);
x2 = data(:,2);

fid = fopen(centers_file, "r");

fgetl(fid);

cx = zeros(M,1);
cy = zeros(M,1);

for k = 1:M
    line = fgetl(fid);
    vals = str2num(line);
    cx(k) = vals(1);
    cy(k) = vals(2);
end

fclose(fid);

figure;
hold on;
plot(x1, x2, "b+");
plot(cx, cy, "r*", "markersize", 12, "linewidth", 2);
xlabel("x1");
ylabel("x2");
title(sprintf("ΣΔΟ: M = %d (σημεία και κέντρα)", M));
axis([0 2 0 2]);
grid on;
hold off;

