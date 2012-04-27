image_bin = function(image)

[h,w] = size(image);

for y = 1:h
    for x = 1:w
        if(image(y,x)<150)
            image(y,x) = 0;
        else
            image(y,x) = 255;
        end
    end
end

imshow(image);
imwrite(image,'circulo1.jpg');
end function