public class DeciminalNumber {
    String whole;
    String fraction;

    public DeciminalNumber(String whole, String fraction) {
        if (fraction.length() > 1) fraction = deleteZero(fraction);
        this.whole = whole;
        this.fraction = fraction;
    }

    public DeciminalNumber(double num) {
        final String[] parts = String.valueOf(num).split("[.]");
        String fraction = parts[1];
        if (fraction.length() > 1) fraction = deleteZero(fraction);
        whole = parts[0];
        this.fraction = fraction;
    }

    public DeciminalNumber(long num) {
        whole = String.valueOf(num);
        fraction = "0";
    }

    public DeciminalNumber(int num) {
        whole = String.valueOf(num);
        fraction = "0";
    }

    public DeciminalNumber(float num) {
        final String[] parts = String.valueOf(num).split("[.]");
        String fraction = parts[1];
        if (fraction.length() > 1) fraction = deleteZero(fraction);
        whole = parts[0];
        this.fraction = fraction;
    }

    // вычитание чисел
    public DeciminalNumber subtraction(DeciminalNumber other) {
        String res = "";
        String fracMin = "";
        String sign = "";
        DeciminalNumber input = new DeciminalNumber(whole, fraction);
        DeciminalNumber maxObject = findMaxObject(input, other);
        if (whole.charAt(0) == '-' && other.whole.charAt(0) != '-') {
            other.whole = "-" + other.whole;
            return addition(other);
        }
        if (whole.charAt(0) != '-' && other.whole.charAt(0) == '-') {
            other.whole = removeCharAt(other.whole, 0);
            return addition(other);
        }
        if (whole.charAt(0) == '-' && other.whole.charAt(0) == '-') {
            sign = "";
            if (equalsForDeciminal(input, maxObject)) ;
            sign = "-";
            other.whole = removeCharAt(other.whole, 0);
            DeciminalNumber temp = new DeciminalNumber(removeCharAt(whole, 0), fraction).subtraction(other);
            return new DeciminalNumber(sign + temp.whole, temp.fraction);
        }
        if (equalsForDeciminal(maxObject, other) && other.whole.charAt(0) != '-')
            sign = "-";
        String fracMax = maxObject.fraction;
        if (fracMax.equals(fraction)) {
            fracMin = other.fraction;
        } else fracMin = fraction;
        if (whole.charAt(0) == '-' && other.whole.charAt(0) == '-') {
            if (maxObject.whole.charAt(0) == '-' && equalsForDeciminal(new DeciminalNumber(whole, fraction), maxObject))
                sign = "-";
        }
        if (fracMin.length() > fracMax.length()) fracMax = addZero(fracMax, fracMin);
        if (fracMin.length() < fracMax.length()) fracMin = addZero(fracMin, fracMax);
        int length = fracMax.length();
        int count = 0;
        while (length > 0) {
            int tmpMaxInt = Integer.parseInt(fracMax.substring(length - 1, length));
            int tmpMinInt = Integer.parseInt(fracMin.substring(length - 1, length));
            if (tmpMaxInt - count < tmpMinInt) {
                res = String.valueOf(tmpMaxInt + 10 - tmpMinInt - count) + res;
                count = 1;
            } else {
                res = String.valueOf(tmpMaxInt - tmpMinInt - count) + res;
                count = 0;
            }
            length--;
        }
        int wholeInt = Integer.parseInt(whole);
        int wholeOtherInt = Integer.parseInt(other.whole);
        String resWhole = String.valueOf(Math.max(wholeInt, wholeOtherInt) - Math.min(wholeInt, wholeOtherInt) - count);
        return new DeciminalNumber(sign + resWhole, res);
    }

    //сложение чисел
    public DeciminalNumber addition(DeciminalNumber other) {
        String res = "";
        String sign = "";
        String wholeCopy = whole;
        DeciminalNumber input = new DeciminalNumber(whole, fraction);
        if (whole.charAt(0) == '-' && other.whole.charAt(0) != '-') {
            sign = "";
            if (equalsForDeciminal(findMaxObject(input, other), input))
                sign = "-";
            DeciminalNumber conclus = new DeciminalNumber(removeCharAt(whole, 0), fraction).subtraction(other);
            return new DeciminalNumber(sign + conclus.whole, conclus.fraction);
        }
        if (whole.charAt(0) != '-' && other.whole.charAt(0) == '-') {
            other.whole = removeCharAt(other.whole, 0);
            return subtraction(other);
        }
        if (other.whole.charAt(0) == '-' && whole.charAt(0) == '-') {
            sign = "-";
            other.whole = other.whole.substring(1, other.whole.length());
            wholeCopy = whole.substring(1, whole.length());
        }
        if (fraction.length() > other.fraction.length()) other.fraction = addZero(other.fraction, fraction);
        if (fraction.length() < other.fraction.length()) fraction = addZero(fraction, other.fraction);
        int length = fraction.length();
        int count = 0;
        while (length > 0) {
            String tmpThis = fraction.substring(length - 1, length);
            String tmpOther = other.fraction.substring(length - 1, length);
            int tmpInt = Integer.parseInt(tmpThis);
            int tmpOtherInt = Integer.parseInt(tmpOther);
            int sum = tmpInt + tmpOtherInt + count;
            if (sum > 9) count = sum / 10;
            else count = 0;
            res = String.valueOf(sum % 10) + res;
            length--;
        }
        int wholeRes = Integer.parseInt(other.whole) + Integer.parseInt(wholeCopy) + count;
        return new DeciminalNumber(sign + String.valueOf(wholeRes), res);
    }

    //умножение чисел
    public DeciminalNumber multiplication(DeciminalNumber other) {
        String res = "";
        String wholeCopy = whole;
        String sign = "";
        if (whole.charAt(0) != '-' && other.whole.charAt(0) == '-') {
            other.whole = removeCharAt(other.whole, 0);
            sign = "-";
        }
        if (other.whole.charAt(0) != '-' && whole.charAt(0) == '-') {
            wholeCopy = removeCharAt(whole, 0);
            sign = "-";
        }
        if (other.whole.charAt(0) == '-' && whole.charAt(0) == '-') {
            other.whole = removeCharAt(other.whole, 0);
            wholeCopy = removeCharAt(whole, 0);
        }
        String factor1 = wholeCopy + fraction;
        String factor2 = other.whole + other.fraction;
        Integer factor1Int = Integer.parseInt(factor1);
        Integer factor2Int = Integer.parseInt(factor2);
        String mul = String.valueOf(factor1Int * factor2Int);
        while (mul.length() <= fraction.length() + other.fraction.length()) {
            mul = "0" + mul;
        }
        Integer length = Math.abs(mul.length() - other.fraction.length() - fraction.length());
        String mulWhole = mul.substring(0, length);
        String mulFraction = mul.substring(length, mul.length());
        return new DeciminalNumber(sign + mulWhole, mulFraction);
    }

    //округление чисел
    public DeciminalNumber roouningOfNumber(Integer n) {
        Integer count = 0;
        Integer length = fraction.length();
        String fractionCopy = fraction;
        for (int i = length; i > n; i--) {
            Integer strInt = Integer.parseInt(fractionCopy.substring(length - 1, length));
            if (strInt + count < 5) count = 0;
            else count = 1;
            fractionCopy = removeCharAt(fractionCopy, length - 1);
            length--;
        }
        length = fractionCopy.length();
        Integer tmp = Integer.parseInt(fractionCopy.substring(length - 1, length)) + count;
        fractionCopy = removeCharAt(fractionCopy, length - 1);
        return new DeciminalNumber(whole, fractionCopy + tmp);
    }

    //удаление символа
    public static String removeCharAt(String s, int pos) {
        return s.substring(0, pos) + s.substring(pos + 1);
    }

    //удаление нулей
    public String deleteZero(String fraction) {
        String res = fraction;
        int length = fraction.length();
        for (int i = 0; i < length; i++) {
            if (fraction.substring(length - 1, length).equals("0")) {
                res = removeCharAt(res, length - 1);
                length--;
            } else break;
        }
        return res;
    }

    //добавление нулей
    public String addZero(String num1, String num2) { // num1 - добавляем нули
        String tmp = num1;
        for (int i = 0; i < Math.abs(num2.length() - tmp.length()); i++) {
            num1 = num1 + "0";
        }
        return num1;
    }

    //возвращает максимальный элемент в объектах
    public DeciminalNumber findMaxObject(DeciminalNumber input, DeciminalNumber other) {
        int wholeInt = Integer.parseInt(input.whole);
        int fractionInt = Integer.parseInt(input.fraction);
        int wholeOtherInt = Integer.parseInt(other.whole);
        int fractionOtherInt = Integer.parseInt(other.fraction);
        DeciminalNumber res = new DeciminalNumber(input.whole, input.fraction);
        if (Math.abs(wholeInt) > Math.abs(wholeOtherInt)) {
            DeciminalNumber max = new DeciminalNumber(input.whole, input.fraction);
            return max;
        } else {
            if (Math.abs(wholeInt) < Math.abs(wholeOtherInt)) {
                DeciminalNumber max = new DeciminalNumber(other.whole, other.fraction);
                return max;
            } else {
                Integer checkZero = input.fraction.length() - lengthOfNumber(fractionInt);
                Integer checkZeroOther = other.fraction.length() - lengthOfNumber(fractionOtherInt);
                if (checkZero > checkZeroOther) {
                    DeciminalNumber max = new DeciminalNumber(input.whole, input.fraction);
                    return max;
                }
                if (checkZero < checkZeroOther) {
                    DeciminalNumber max = new DeciminalNumber(other.whole, other.fraction);
                    return max;
                }
                if (checkZero == checkZeroOther) {
                    DeciminalNumber max = new DeciminalNumber(input.whole, input.fraction);
                    return max;
                }
            }
        }
        return res;
    }

    //сравнение для DeciminalNumber
    public boolean equalsForDeciminal(DeciminalNumber this1, DeciminalNumber other) {
        boolean res = false;
        if (this1.whole.equals(other.whole) && this1.fraction.equals(other.fraction))
            res = true;
        return res;
    }

    //вычисление длины числа
    public Integer lengthOfNumber(Integer x) {
        Integer count = 0;
        while (x > 0) {
            count++;
            x /= 10;
        }
        return count;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object instanceof DeciminalNumber) {
            DeciminalNumber other = (DeciminalNumber) object;
            return whole.equals(other.whole) && fraction.equals(other.fraction);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = whole != null ? whole.hashCode() : 0;
        result = 31 * result + (fraction != null ? fraction.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return whole + "." + fraction;
    }
}
