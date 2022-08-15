import java.util.ArrayList;
import java.util.List;

public class BlendedIndex{
    
    public static double Return(double previous_value , double next_value){
        double return_value = (next_value/previous_value) -1;
        return return_value;
    }

    public static double[] get_Return_Array(double[] index){
        double[] return_array = new double[index.length-1];
        for (int index_size = 0; index_size<index.length-1; index_size++){
            return_array[index_size] = Return(index[index_size], index[index_size+1]);  
        }
        return return_array;
    }
  
    public static double Weighted_Return(double return_value , double index_configuration){
        double weighted_return = return_value*index_configuration;
        return weighted_return;
    }

    public static double[] get_Weighted_Return_Array(List<double[]> return_arrays , double[] index_configuration ){
        double[] weighted_return_list = new double[return_arrays.get(0).length];
        for (int i = 0; i<return_arrays.get(0).length;i++){
            double weighted_return = 0;
            for (int j = 0; j<return_arrays.size(); j++){
                weighted_return += (Weighted_Return(return_arrays.get(j)[i], index_configuration[j]));
            }
            weighted_return_list[i] = weighted_return;
        }
        return weighted_return_list;
    }

    public static double Price_Weighted(double index_value , double index_configuration){
        double price_weighted_return = index_value*index_configuration;
        return price_weighted_return;
    }

    public static double[] get_Price_Weighted_Array(List<double[]> indexes , double[] index_configuration ){
        double[]price_weighted_list = new double[indexes.get(0).length];
        for (int i = 0; i<indexes.get(0).length;i++){
            double price_weight = 0;
            for (int j = 0; j<indexes.size(); j++){
                price_weight += (Price_Weighted(indexes.get(j)[i], index_configuration[j]));
            }
            price_weighted_list[i] = price_weight;
        }
        return price_weighted_list;
    }

    public static double Price_Weighted_with_Rebased(double price_weighted_previous,double price_weighted_current, double price_weighted_with_rebased_previous_value){
        double price_weighted_with_rebased = (price_weighted_current / price_weighted_previous)*price_weighted_with_rebased_previous_value;
        return price_weighted_with_rebased;
    }

    public static double[] Price_Weighted_with_Rebased_Array(double[] price_weight_list , double initial_value){
        
        double[] price_weighted_with_rebased_list = new double[price_weight_list.length];
        price_weighted_with_rebased_list[0] = initial_value;
        double pwwr = initial_value;
        for (int i=0; i<price_weight_list.length-1;i++){
            double value = Price_Weighted_with_Rebased(price_weight_list[i], price_weight_list[i+1], pwwr);
            price_weighted_with_rebased_list[i+1] = value;
            pwwr = value;
        }
        return price_weighted_with_rebased_list;
    }

    public static double Return_Weighted(double weighted_return_current_value ,  double return_weighted_previous_value){
        double return_weighted = (1 + weighted_return_current_value)*return_weighted_previous_value;
        return return_weighted;
    }

    public static double[] Return_Weighted_Array(double[] weighted_return_list , double initial_value){
        
        double[] return_weighted_list = new double[weighted_return_list.length+1];
        return_weighted_list[0] = initial_value;
        double rw = initial_value;
        for (int i=0; i<weighted_return_list.length;i++){
            double value = Return_Weighted(weighted_return_list[i], rw);
            return_weighted_list[i+1] = value;
            rw = value;
        }
        return return_weighted_list;
    }

    public static double Addition_Of_Constant(double index1_return , double addition_of_constant_previous_value , double constant_configuration){
        double addition_of_constant = (1 + index1_return)*addition_of_constant_previous_value + constant_configuration/365;
        return addition_of_constant;
    }

    public static double[] Addition_Of_Constant_Array(double[] index1_return_list , double initial_value,double constant_configuration){
        
        double[] addition_of_constant_list = new double[index1_return_list.length+1];
        addition_of_constant_list[0] = initial_value;
        double aoc = initial_value;
        for (int i=0; i<index1_return_list.length;i++){
            double value = Addition_Of_Constant(index1_return_list[i], aoc,constant_configuration);
            addition_of_constant_list[i+1] = value;
            aoc = value;
        }
        return addition_of_constant_list;
    }

    public static void main(String[] args) {
        double[] index_1 = new double[]{444,444.6,444.7,444.5,444.9,444.4,444.4,444.2,444.3,444.6,444.3,444.3,444.2,444.1,444};
        double[] index_2 = new double[]{333,336.33,339.6933,346.487166,353.4169093,353.4169093,371.0877548,363.6659997,356.3926797,356.3926797,356.3926797,349.2648261,352.7574744,366.8677733,366.8677733};
        double[] index_configuration = new double[]{0.3,0.7};
        double constant_configuration = 0.03;


        List<double[]> indexes = new ArrayList<>();
        indexes.add(index_1);
        indexes.add(index_2);

        // -----------test additional calculation results-----------------
        List<double[]> return_arrays = new ArrayList<>();
        for (int number_of_indexes = 0; number_of_indexes<indexes.size(); number_of_indexes++){
            double[] return_array = get_Return_Array(indexes.get(number_of_indexes));
            return_arrays.add(return_array);
        }

        double[] weighted_return_list = get_Weighted_Return_Array(return_arrays,index_configuration);
        System.out.println("Weighted Return");
        for (int i=0; i<weighted_return_list.length;i++){
            System.out.println(weighted_return_list[i]);
        }


        // -----------test the output results-----------------
        // 1. price weighted
        System.out.println("Price Weighted");
        double[] price_weight_list = get_Price_Weighted_Array(indexes,index_configuration);
        for (int i=0; i<price_weight_list.length;i++){
            System.out.println(price_weight_list[i]);
        }

        // 2. Price_Weighted_with_Rebased
        System.out.println("Price Weighted with Rebased");
        double[] price_weighted_with_rebased_list = Price_Weighted_with_Rebased_Array(price_weight_list,100);
        for (int i=0; i<price_weighted_with_rebased_list.length;i++){
            System.out.println(price_weighted_with_rebased_list[i]);
        }

        // 3. Return Weighted
        System.out.println("Return Weighted");
        double[] return_weighted_list = Return_Weighted_Array(weighted_return_list, 100);
        for (int i=0; i<return_weighted_list.length;i++){
            System.out.println(return_weighted_list[i]);
        }

        // 4. Addition of Constant
        System.out.println("Addition of Constant");
        double[] addition_of_constant_list = Addition_Of_Constant_Array(get_Return_Array(indexes.get(0)), 100, constant_configuration);
        for (int i=0; i<addition_of_constant_list.length;i++){
            System.out.println(addition_of_constant_list[i]);
        }

    }
}
