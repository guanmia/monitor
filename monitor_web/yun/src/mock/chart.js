import { param2Obj } from '@/utils'

const inter = {
  "error_code": 0,    
  "data": {
     "total":100,
     "results":
     [
         {
            "id":110,
            "regionName": "北京"
         },
         {
          "id":111,
          "regionName": "成都"
       },
     ]      
  }
}
const lines = {
  "110": {
    "out":[
      [332, 820, 354, 695, 245, 351, 136, 2, 712, 202, 800, 65, 834, 849, 676, 364, 979, 287, 852, 235, 140, 776, 45, 29, 807, 633, 807, 177, 753, 981, 516, 671, 497, 510, 764, 593, 565, 318, 743, 526, 593, 489, 717, 431, 377, 144, 716, 553, 725, 175, 996, 317, 766, 348, 936, 732, 732, 931, 499, 923, 442, 878, 484, 186, 885, 477, 325, 554, 787, 261, 390, 12, 280, 635, 323, 452, 485, 589, 152, 691, 500, 413, 117, 276, 566, 338, 932, 69, 427, 294, 812, 453, 134, 901, 89, 399, 702, 106, 270, 749, 405, 314, 589, 127, 527, 252, 547, 828, 30, 921, 330, 318, 921, 844, 147, 479, 313, 711, 507, 629, 373, 373, 361, 135, 816, 544, 97, 399, 868, 985, 808, 438, 883, 63, 928, 913, 999, 354, 589, 366, 534, 237, 658, 726, 647, 28, 335, 554, 374, 797, 631, 427, 879, 386, 33, 25, 404, 163, 57, 226, 243, 973, 940, 294, 759, 489, 915, 280, 82, 400, 297, 618, 811, 401, 809, 131, 342, 298, 311, 672, 757, 530, 721, 142, 601, 292, 498, 851, 567, 812, 934, 337, 818, 591, 276, 37, 445, 512, 387, 642, 942, 22, 577, 60, 245, 902, 498, 584, 642, 178, 58, 472, 30, 310, 190, 892, 941, 535, 898, 41, 557, 854, 530, 455, 718, 884, 127, 97, 451, 618, 52, 931, 696, 545, 387, 512, 526, 908, 309, 587, 556, 335, 289, 304, 885, 69, 949, 705, 193, 615, 696, 142, 2, 609, 258, 818, 110, 11, 597, 311, 452, 440, 233, 904, 23, 667, 799, 763, 583, 907, 783, 675, 918, 112, 204, 951, 926, 421, 416, 45, 401, 67, 297, 367, 7, 76, 986, 983],
      [552, 65, 470, 759, 98, 783, 190, 332, 379, 447, 824, 763, 214, 871, 468, 802, 839, 517, 507, 848, 19, 825, 176, 681, 115, 333, 495, 480, 182, 334, 384, 1000, 870, 168, 752, 987, 309, 572, 640, 351, 716, 579, 901, 58, 343, 341, 186, 130, 728, 809, 705, 72, 694, 315, 167, 88, 245, 635, 400, 408, 942, 953, 363, 123, 205, 701, 634, 829, 560, 903, 340, 647, 130, 576, 862, 805, 637, 77, 542, 384, 214, 831, 723, 535, 829, 827, 210, 143, 927, 386, 366, 540, 552, 690, 556, 565, 435, 76, 7, 838, 73, 890, 55, 737, 124, 464, 143, 426, 897, 818, 233, 86, 166, 868, 412, 439, 781, 842, 456, 658, 882, 870, 24, 392, 527, 813, 573, 837, 37, 477, 552, 785, 712, 48, 731, 574, 733, 452, 32, 864, 545, 892, 812, 148, 700, 397, 663, 938, 972, 56, 30, 45, 796, 755, 706, 785, 575, 433, 196, 41, 504, 972, 155, 459, 266, 608, 955, 625, 438, 566, 511, 687, 714, 974, 80, 166, 956, 577, 143, 752, 14, 674, 920, 790, 429, 521, 688, 35, 184, 222, 635, 20, 554, 853, 542, 210, 228, 456, 421, 875, 477, 847, 37, 240, 620, 586, 493, 645, 11, 40, 882, 665, 222, 593, 724, 555, 928, 527, 931, 809, 938, 498, 69, 343, 860, 983, 329, 291, 650, 659, 324, 654, 70, 802, 489, 724, 811, 390, 90, 800, 261, 683, 142, 198, 831, 913, 543, 312, 384, 200, 282, 632, 928, 613, 77, 553, 430, 616, 382, 258, 131, 774, 207, 54, 730, 328, 932, 793, 312, 849, 607, 394, 171, 768, 198, 33, 137, 511, 589, 109, 609, 154, 118, 745, 520, 972, 274, 419],
      [880, 22, 473, 510, 527]
    ], 
    "in":[
      [332, 820, 354, 695, 245, 351, 136, 2, 712, 202, 800, 65, 834, 849, 676, 364, 979, 287, 852, 235, 140, 776, 45, 29, 807, 633, 807, 177, 753, 981, 516, 671, 497, 510, 764, 593, 565, 318, 743, 526, 593, 489, 717, 431, 377, 144, 716, 553, 725, 175, 996, 317, 766, 348, 936, 732, 732, 931, 499, 923, 442, 878, 484, 186, 885, 477, 325, 554, 787, 261, 390, 12, 280, 635, 323, 452, 485, 589, 152, 691, 500, 413, 117, 276, 566, 338, 932, 69, 427, 294, 812, 453, 134, 901, 89, 399, 702, 106, 270, 749, 405, 314, 589, 127, 527, 252, 547, 828, 30, 921, 330, 318, 921, 844, 147, 479, 313, 711, 507, 629, 373, 373, 361, 135, 816, 544, 97, 399, 868, 985, 808, 438, 883, 63, 928, 913, 999, 354, 589, 366, 534, 237, 658, 726, 647, 28, 335, 554, 374, 797, 631, 427, 879, 386, 33, 25, 404, 163, 57, 226, 243, 973, 940, 294, 759, 489, 915, 280, 82, 400, 297, 618, 811, 401, 809, 131, 342, 298, 311, 672, 757, 530, 721, 142, 601, 292, 498, 851, 567, 812, 934, 337, 818, 591, 276, 37, 445, 512, 387, 642, 942, 22, 577, 60, 245, 902, 498, 584, 642, 178, 58, 472, 30, 310, 190, 892, 941, 535, 898, 41, 557, 854, 530, 455, 718, 884, 127, 97, 451, 618, 52, 931, 696, 545, 387, 512, 526, 908, 309, 587, 556, 335, 289, 304, 885, 69, 949, 705, 193, 615, 696, 142, 2, 609, 258, 818, 110, 11, 597, 311, 452, 440, 233, 904, 23, 667, 799, 763, 583, 907, 783, 675, 918, 112, 204, 951, 926, 421, 416, 45, 401, 67, 297, 367, 7, 76, 986, 983],
      [552, 65, 470, 759, 98, 783, 190, 332, 379, 447, 824, 763, 214, 871, 468, 802, 839, 517, 507, 848, 19, 825, 176, 681, 115, 333, 495, 480, 182, 334, 384, 1000, 870, 168, 752, 987, 309, 572, 640, 351, 716, 579, 901, 58, 343, 341, 186, 130, 728, 809, 705, 72, 694, 315, 167, 88, 245, 635, 400, 408, 942, 953, 363, 123, 205, 701, 634, 829, 560, 903, 340, 647, 130, 576, 862, 805, 637, 77, 542, 384, 214, 831, 723, 535, 829, 827, 210, 143, 927, 386, 366, 540, 552, 690, 556, 565, 435, 76, 7, 838, 73, 890, 55, 737, 124, 464, 143, 426, 897, 818, 233, 86, 166, 868, 412, 439, 781, 842, 456, 658, 882, 870, 24, 392, 527, 813, 573, 837, 37, 477, 552, 785, 712, 48, 731, 574, 733, 452, 32, 864, 545, 892, 812, 148, 700, 397, 663, 938, 972, 56, 30, 45, 796, 755, 706, 785, 575, 433, 196, 41, 504, 972, 155, 459, 266, 608, 955, 625, 438, 566, 511, 687, 714, 974, 80, 166, 956, 577, 143, 752, 14, 674, 920, 790, 429, 521, 688, 35, 184, 222, 635, 20, 554, 853, 542, 210, 228, 456, 421, 875, 477, 847, 37, 240, 620, 586, 493, 645, 11, 40, 882, 665, 222, 593, 724, 555, 928, 527, 931, 809, 938, 498, 69, 343, 860, 983, 329, 291, 650, 659, 324, 654, 70, 802, 489, 724, 811, 390, 90, 800, 261, 683, 142, 198, 831, 913, 543, 312, 384, 200, 282, 632, 928, 613, 77, 553, 430, 616, 382, 258, 131, 774, 207, 54, 730, 328, 932, 793, 312, 849, 607, 394, 171, 768, 198, 33, 137, 511, 589, 109, 609, 154, 118, 745, 520, 972, 274, 419],
      [880, 22, 473, 510, 527]
    ], 
    "cpu":[
      [332, 820, 354, 695, 245, 351, 136, 2, 712, 202, 800, 65, 834, 849, 676, 364, 979, 287, 852, 235, 140, 776, 45, 29, 807, 633, 807, 177, 753, 981, 516, 671, 497, 510, 764, 593, 565, 318, 743, 526, 593, 489, 717, 431, 377, 144, 716, 553, 725, 175, 996, 317, 766, 348, 936, 732, 732, 931, 499, 923, 442, 878, 484, 186, 885, 477, 325, 554, 787, 261, 390, 12, 280, 635, 323, 452, 485, 589, 152, 691, 500, 413, 117, 276, 566, 338, 932, 69, 427, 294, 812, 453, 134, 901, 89, 399, 702, 106, 270, 749, 405, 314, 589, 127, 527, 252, 547, 828, 30, 921, 330, 318, 921, 844, 147, 479, 313, 711, 507, 629, 373, 373, 361, 135, 816, 544, 97, 399, 868, 985, 808, 438, 883, 63, 928, 913, 999, 354, 589, 366, 534, 237, 658, 726, 647, 28, 335, 554, 374, 797, 631, 427, 879, 386, 33, 25, 404, 163, 57, 226, 243, 973, 940, 294, 759, 489, 915, 280, 82, 400, 297, 618, 811, 401, 809, 131, 342, 298, 311, 672, 757, 530, 721, 142, 601, 292, 498, 851, 567, 812, 934, 337, 818, 591, 276, 37, 445, 512, 387, 642, 942, 22, 577, 60, 245, 902, 498, 584, 642, 178, 58, 472, 30, 310, 190, 892, 941, 535, 898, 41, 557, 854, 530, 455, 718, 884, 127, 97, 451, 618, 52, 931, 696, 545, 387, 512, 526, 908, 309, 587, 556, 335, 289, 304, 885, 69, 949, 705, 193, 615, 696, 142, 2, 609, 258, 818, 110, 11, 597, 311, 452, 440, 233, 904, 23, 667, 799, 763, 583, 907, 783, 675, 918, 112, 204, 951, 926, 421, 416, 45, 401, 67, 297, 367, 7, 76, 986, 983],
      [552, 65, 470, 759, 98, 783, 190, 332, 379, 447, 824, 763, 214, 871, 468, 802, 839, 517, 507, 848, 19, 825, 176, 681, 115, 333, 495, 480, 182, 334, 384, 1000, 870, 168, 752, 987, 309, 572, 640, 351, 716, 579, 901, 58, 343, 341, 186, 130, 728, 809, 705, 72, 694, 315, 167, 88, 245, 635, 400, 408, 942, 953, 363, 123, 205, 701, 634, 829, 560, 903, 340, 647, 130, 576, 862, 805, 637, 77, 542, 384, 214, 831, 723, 535, 829, 827, 210, 143, 927, 386, 366, 540, 552, 690, 556, 565, 435, 76, 7, 838, 73, 890, 55, 737, 124, 464, 143, 426, 897, 818, 233, 86, 166, 868, 412, 439, 781, 842, 456, 658, 882, 870, 24, 392, 527, 813, 573, 837, 37, 477, 552, 785, 712, 48, 731, 574, 733, 452, 32, 864, 545, 892, 812, 148, 700, 397, 663, 938, 972, 56, 30, 45, 796, 755, 706, 785, 575, 433, 196, 41, 504, 972, 155, 459, 266, 608, 955, 625, 438, 566, 511, 687, 714, 974, 80, 166, 956, 577, 143, 752, 14, 674, 920, 790, 429, 521, 688, 35, 184, 222, 635, 20, 554, 853, 542, 210, 228, 456, 421, 875, 477, 847, 37, 240, 620, 586, 493, 645, 11, 40, 882, 665, 222, 593, 724, 555, 928, 527, 931, 809, 938, 498, 69, 343, 860, 983, 329, 291, 650, 659, 324, 654, 70, 802, 489, 724, 811, 390, 90, 800, 261, 683, 142, 198, 831, 913, 543, 312, 384, 200, 282, 632, 928, 613, 77, 553, 430, 616, 382, 258, 131, 774, 207, 54, 730, 328, 932, 793, 312, 849, 607, 394, 171, 768, 198, 33, 137, 511, 589, 109, 609, 154, 118, 745, 520, 972, 274, 419],
      [880, 22, 473, 510, 527]
    ],
    "memory":[
      [332, 820, 354, 695, 245, 351, 136, 2, 712, 202, 800, 65, 834, 849, 676, 364, 979, 287, 852, 235, 140, 776, 45, 29, 807, 633, 807, 177, 753, 981, 516, 671, 497, 510, 764, 593, 565, 318, 743, 526, 593, 489, 717, 431, 377, 144, 716, 553, 725, 175, 996, 317, 766, 348, 936, 732, 732, 931, 499, 923, 442, 878, 484, 186, 885, 477, 325, 554, 787, 261, 390, 12, 280, 635, 323, 452, 485, 589, 152, 691, 500, 413, 117, 276, 566, 338, 932, 69, 427, 294, 812, 453, 134, 901, 89, 399, 702, 106, 270, 749, 405, 314, 589, 127, 527, 252, 547, 828, 30, 921, 330, 318, 921, 844, 147, 479, 313, 711, 507, 629, 373, 373, 361, 135, 816, 544, 97, 399, 868, 985, 808, 438, 883, 63, 928, 913, 999, 354, 589, 366, 534, 237, 658, 726, 647, 28, 335, 554, 374, 797, 631, 427, 879, 386, 33, 25, 404, 163, 57, 226, 243, 973, 940, 294, 759, 489, 915, 280, 82, 400, 297, 618, 811, 401, 809, 131, 342, 298, 311, 672, 757, 530, 721, 142, 601, 292, 498, 851, 567, 812, 934, 337, 818, 591, 276, 37, 445, 512, 387, 642, 942, 22, 577, 60, 245, 902, 498, 584, 642, 178, 58, 472, 30, 310, 190, 892, 941, 535, 898, 41, 557, 854, 530, 455, 718, 884, 127, 97, 451, 618, 52, 931, 696, 545, 387, 512, 526, 908, 309, 587, 556, 335, 289, 304, 885, 69, 949, 705, 193, 615, 696, 142, 2, 609, 258, 818, 110, 11, 597, 311, 452, 440, 233, 904, 23, 667, 799, 763, 583, 907, 783, 675, 918, 112, 204, 951, 926, 421, 416, 45, 401, 67, 297, 367, 7, 76, 986, 983],
      [552, 65, 470, 759, 98, 783, 190, 332, 379, 447, 824, 763, 214, 871, 468, 802, 839, 517, 507, 848, 19, 825, 176, 681, 115, 333, 495, 480, 182, 334, 384, 1000, 870, 168, 752, 987, 309, 572, 640, 351, 716, 579, 901, 58, 343, 341, 186, 130, 728, 809, 705, 72, 694, 315, 167, 88, 245, 635, 400, 408, 942, 953, 363, 123, 205, 701, 634, 829, 560, 903, 340, 647, 130, 576, 862, 805, 637, 77, 542, 384, 214, 831, 723, 535, 829, 827, 210, 143, 927, 386, 366, 540, 552, 690, 556, 565, 435, 76, 7, 838, 73, 890, 55, 737, 124, 464, 143, 426, 897, 818, 233, 86, 166, 868, 412, 439, 781, 842, 456, 658, 882, 870, 24, 392, 527, 813, 573, 837, 37, 477, 552, 785, 712, 48, 731, 574, 733, 452, 32, 864, 545, 892, 812, 148, 700, 397, 663, 938, 972, 56, 30, 45, 796, 755, 706, 785, 575, 433, 196, 41, 504, 972, 155, 459, 266, 608, 955, 625, 438, 566, 511, 687, 714, 974, 80, 166, 956, 577, 143, 752, 14, 674, 920, 790, 429, 521, 688, 35, 184, 222, 635, 20, 554, 853, 542, 210, 228, 456, 421, 875, 477, 847, 37, 240, 620, 586, 493, 645, 11, 40, 882, 665, 222, 593, 724, 555, 928, 527, 931, 809, 938, 498, 69, 343, 860, 983, 329, 291, 650, 659, 324, 654, 70, 802, 489, 724, 811, 390, 90, 800, 261, 683, 142, 198, 831, 913, 543, 312, 384, 200, 282, 632, 928, 613, 77, 553, 430, 616, 382, 258, 131, 774, 207, 54, 730, 328, 932, 793, 312, 849, 607, 394, 171, 768, 198, 33, 137, 511, 589, 109, 609, 154, 118, 745, 520, 972, 274, 419],
      [880, 22, 473, 510, 527]
    ],
    "disk":[202, 800, 1000, 834, 849, 676, 364, 979, 287, 852, 235, 140, 776, 45, 29, 807, 633, 807, 177, 753, 981, 516, 671, 497, 510, 764, 593, 565, 318, 743, 526, 593, 489, 717, 431, 377, 144, 716, 553, 725, 175, 996, 317, 766, 348, 936, 732, 732, 931, 499, 923, 442, 878, 484, 186, 885, 477, 325, 554, 787, 261, 390, 12, 280, 635, 323, 452, 485, 589, 152, 552, 65, 470, 759, 98, 783, 190, 332, 379, 447, 824, 763, 214, 871, 468, 802, 839, 517, 507, 848, 19, 825, 176, 681, 115, 333, 495, 480, 838, 73],
    "nodes":[["武汉", "长沙", "厦门", "上海", "西安", "成都", "北京"],[43, 56, 27, 35, 16, 23, 48]]
  },
  "111": {
    "out":[
      [332, 820, 354, 695, 245, 351, 136, 2, 712, 202, 800, 65, 834, 849, 676, 364, 979, 287, 852, 235, 140, 776, 45, 29, 807, 633, 807, 177, 753, 981, 516, 671, 497, 510, 764, 593, 565, 318, 743, 526, 593, 489, 717, 431, 377, 144, 716, 553, 725, 175, 996, 317, 766, 348, 936, 732, 732, 931, 499, 923, 442, 878, 484, 186, 885, 477, 325, 554, 787, 261, 390, 12, 280, 635, 323, 452, 485, 589, 152, 691, 500, 413, 117, 276, 566, 338, 932, 69, 427, 294, 812, 453, 134, 901, 89, 399, 702, 106, 270, 749, 405, 314, 589, 127, 527, 252, 547, 828, 30, 921, 330, 318, 921, 844, 147, 479, 313, 711, 507, 629, 373, 373, 361, 135, 816, 544, 97, 399, 868, 985, 808, 438, 883, 63, 928, 913, 999, 354, 589, 366, 534, 237, 658, 726, 647, 28, 335, 554, 374, 797, 631, 427, 879, 386, 33, 25, 404, 163, 57, 226, 243, 973, 940, 294, 759, 489, 915, 280, 82, 400, 297, 618, 811, 401, 809, 131, 342, 298, 311, 672, 757, 530, 721, 142, 601, 292, 498, 851, 567, 812, 934, 337, 818, 591, 276, 37, 445, 512, 387, 642, 942, 22, 577, 60, 245, 902, 498, 584, 642, 178, 58, 472, 30, 310, 190, 892, 941, 535, 898, 41, 557, 854, 530, 455, 718, 884, 127, 97, 451, 618, 52, 931, 696, 545, 387, 512, 526, 908, 309, 587, 556, 335, 289, 304, 885, 69, 949, 705, 193, 615, 696, 142, 2, 609, 258, 818, 110, 11, 597, 311, 452, 440, 233, 904, 23, 667, 799, 763, 583, 907, 783, 675, 918, 112, 204, 951, 926, 421, 416, 45, 401, 67, 297, 367, 7, 76, 986, 983], 
      [552, 65, 470, 759, 98, 783, 190, 332, 379, 447, 824, 763, 214, 871, 468, 802, 839, 517, 507, 848, 19, 825, 176, 681, 115, 333, 495, 480, 182, 334, 384, 1000, 870, 168, 752, 987, 309, 572, 640, 351, 716, 579, 901, 58, 343, 341, 186, 130, 728, 809, 705, 72, 694, 315, 167, 88, 245, 635, 400, 408, 942, 953, 363, 123, 205, 701, 634, 829, 560, 903, 340, 647, 130, 576, 862, 805, 637, 77, 542, 384, 214, 831, 723, 535, 829, 827, 210, 143, 927, 386, 366, 540, 552, 690, 556, 565, 435, 76, 7, 838, 73, 890, 55, 737, 124, 464, 143, 426, 897, 818, 233, 86, 166, 868, 412, 439, 781, 842, 456, 658, 882, 870, 24, 392, 527, 813, 573, 837, 37, 477, 552, 785, 712, 48, 731, 574, 733, 452, 32, 864, 545, 892, 812, 148, 700, 397, 663, 938, 972, 56, 30, 45, 796, 755, 706, 785, 575, 433, 196, 41, 504, 972, 155, 459, 266, 608, 955, 625, 438, 566, 511, 687, 714, 974, 80, 166, 956, 577, 143, 752, 14, 674, 920, 790, 429, 521, 688, 35, 184, 222, 635, 20, 554, 853, 542, 210, 228, 456, 421, 875, 477, 847, 37, 240, 620, 586, 493, 645, 11, 40, 882, 665, 222, 593, 724, 555, 928, 527, 931, 809, 938, 498, 69, 343, 860, 983, 329, 291, 650, 659, 324, 654, 70, 802, 489, 724, 811, 390, 90, 800, 261, 683, 142, 198, 831, 913, 543, 312, 384, 200, 282, 632, 928, 613, 77, 553, 430, 616, 382, 258, 131, 774, 207, 54, 730, 328, 932, 793, 312, 849, 607, 394, 171, 768, 198, 33, 137, 511, 589, 109, 609, 154, 118, 745, 520, 972, 274, 419], 
      [880, 1000, 473, 510, 527]
    ],
    "in":[
      [332, 820, 354, 695, 245, 351, 136, 2, 712, 202, 800, 65, 834, 849, 676, 364, 979, 287, 852, 235, 140, 776, 45, 29, 807, 633, 807, 177, 753, 981, 516, 671, 497, 510, 764, 593, 565, 318, 743, 526, 593, 489, 717, 431, 377, 144, 716, 553, 725, 175, 996, 317, 766, 348, 936, 732, 732, 931, 499, 923, 442, 878, 484, 186, 885, 477, 325, 554, 787, 261, 390, 12, 280, 635, 323, 452, 485, 589, 152, 691, 500, 413, 117, 276, 566, 338, 932, 69, 427, 294, 812, 453, 134, 901, 89, 399, 702, 106, 270, 749, 405, 314, 589, 127, 527, 252, 547, 828, 30, 921, 330, 318, 921, 844, 147, 479, 313, 711, 507, 629, 373, 373, 361, 135, 816, 544, 97, 399, 868, 985, 808, 438, 883, 63, 928, 913, 999, 354, 589, 366, 534, 237, 658, 726, 647, 28, 335, 554, 374, 797, 631, 427, 879, 386, 33, 25, 404, 163, 57, 226, 243, 973, 940, 294, 759, 489, 915, 280, 82, 400, 297, 618, 811, 401, 809, 131, 342, 298, 311, 672, 757, 530, 721, 142, 601, 292, 498, 851, 567, 812, 934, 337, 818, 591, 276, 37, 445, 512, 387, 642, 942, 22, 577, 60, 245, 902, 498, 584, 642, 178, 58, 472, 30, 310, 190, 892, 941, 535, 898, 41, 557, 854, 530, 455, 718, 884, 127, 97, 451, 618, 52, 931, 696, 545, 387, 512, 526, 908, 309, 587, 556, 335, 289, 304, 885, 69, 949, 705, 193, 615, 696, 142, 2, 609, 258, 818, 110, 11, 597, 311, 452, 440, 233, 904, 23, 667, 799, 763, 583, 907, 783, 675, 918, 112, 204, 951, 926, 421, 416, 45, 401, 67, 297, 367, 7, 76, 986, 983],
      [552, 65, 470, 759, 98, 783, 190, 332, 379, 447, 824, 763, 214, 871, 468, 802, 839, 517, 507, 848, 19, 825, 176, 681, 115, 333, 495, 480, 182, 334, 384, 1000, 870, 168, 752, 987, 309, 572, 640, 351, 716, 579, 901, 58, 343, 341, 186, 130, 728, 809, 705, 72, 694, 315, 167, 88, 245, 635, 400, 408, 942, 953, 363, 123, 205, 701, 634, 829, 560, 903, 340, 647, 130, 576, 862, 805, 637, 77, 542, 384, 214, 831, 723, 535, 829, 827, 210, 143, 927, 386, 366, 540, 552, 690, 556, 565, 435, 76, 7, 838, 73, 890, 55, 737, 124, 464, 143, 426, 897, 818, 233, 86, 166, 868, 412, 439, 781, 842, 456, 658, 882, 870, 24, 392, 527, 813, 573, 837, 37, 477, 552, 785, 712, 48, 731, 574, 733, 452, 32, 864, 545, 892, 812, 148, 700, 397, 663, 938, 972, 56, 30, 45, 796, 755, 706, 785, 575, 433, 196, 41, 504, 972, 155, 459, 266, 608, 955, 625, 438, 566, 511, 687, 714, 974, 80, 166, 956, 577, 143, 752, 14, 674, 920, 790, 429, 521, 688, 35, 184, 222, 635, 20, 554, 853, 542, 210, 228, 456, 421, 875, 477, 847, 37, 240, 620, 586, 493, 645, 11, 40, 882, 665, 222, 593, 724, 555, 928, 527, 931, 809, 938, 498, 69, 343, 860, 983, 329, 291, 650, 659, 324, 654, 70, 802, 489, 724, 811, 390, 90, 800, 261, 683, 142, 198, 831, 913, 543, 312, 384, 200, 282, 632, 928, 613, 77, 553, 430, 616, 382, 258, 131, 774, 207, 54, 730, 328, 932, 793, 312, 849, 607, 394, 171, 768, 198, 33, 137, 511, 589, 109, 609, 154, 118, 745, 520, 972, 274, 419],
      [880, 1000, 473, 510, 527]
    ],
    "cpu":[
      [332, 820, 354, 695, 245, 351, 136, 2, 712, 202, 800, 65, 834, 849, 676, 364, 979, 287, 852, 235, 140, 776, 45, 29, 807, 633, 807, 177, 753, 981, 516, 671, 497, 510, 764, 593, 565, 318, 743, 526, 593, 489, 717, 431, 377, 144, 716, 553, 725, 175, 996, 317, 766, 348, 936, 732, 732, 931, 499, 923, 442, 878, 484, 186, 885, 477, 325, 554, 787, 261, 390, 12, 280, 635, 323, 452, 485, 589, 152, 691, 500, 413, 117, 276, 566, 338, 932, 69, 427, 294, 812, 453, 134, 901, 89, 399, 702, 106, 270, 749, 405, 314, 589, 127, 527, 252, 547, 828, 30, 921, 330, 318, 921, 844, 147, 479, 313, 711, 507, 629, 373, 373, 361, 135, 816, 544, 97, 399, 868, 985, 808, 438, 883, 63, 928, 913, 999, 354, 589, 366, 534, 237, 658, 726, 647, 28, 335, 554, 374, 797, 631, 427, 879, 386, 33, 25, 404, 163, 57, 226, 243, 973, 940, 294, 759, 489, 915, 280, 82, 400, 297, 618, 811, 401, 809, 131, 342, 298, 311, 672, 757, 530, 721, 142, 601, 292, 498, 851, 567, 812, 934, 337, 818, 591, 276, 37, 445, 512, 387, 642, 942, 22, 577, 60, 245, 902, 498, 584, 642, 178, 58, 472, 30, 310, 190, 892, 941, 535, 898, 41, 557, 854, 530, 455, 718, 884, 127, 97, 451, 618, 52, 931, 696, 545, 387, 512, 526, 908, 309, 587, 556, 335, 289, 304, 885, 69, 949, 705, 193, 615, 696, 142, 2, 609, 258, 818, 110, 11, 597, 311, 452, 440, 233, 904, 23, 667, 799, 763, 583, 907, 783, 675, 918, 112, 204, 951, 926, 421, 416, 45, 401, 67, 297, 367, 7, 76, 986, 983],
      [552, 65, 470, 759, 98, 783, 190, 332, 379, 447, 824, 763, 214, 871, 468, 802, 839, 517, 507, 848, 19, 825, 176, 681, 115, 333, 495, 480, 182, 334, 384, 1000, 870, 168, 752, 987, 309, 572, 640, 351, 716, 579, 901, 58, 343, 341, 186, 130, 728, 809, 705, 72, 694, 315, 167, 88, 245, 635, 400, 408, 942, 953, 363, 123, 205, 701, 634, 829, 560, 903, 340, 647, 130, 576, 862, 805, 637, 77, 542, 384, 214, 831, 723, 535, 829, 827, 210, 143, 927, 386, 366, 540, 552, 690, 556, 565, 435, 76, 7, 838, 73, 890, 55, 737, 124, 464, 143, 426, 897, 818, 233, 86, 166, 868, 412, 439, 781, 842, 456, 658, 882, 870, 24, 392, 527, 813, 573, 837, 37, 477, 552, 785, 712, 48, 731, 574, 733, 452, 32, 864, 545, 892, 812, 148, 700, 397, 663, 938, 972, 56, 30, 45, 796, 755, 706, 785, 575, 433, 196, 41, 504, 972, 155, 459, 266, 608, 955, 625, 438, 566, 511, 687, 714, 974, 80, 166, 956, 577, 143, 752, 14, 674, 920, 790, 429, 521, 688, 35, 184, 222, 635, 20, 554, 853, 542, 210, 228, 456, 421, 875, 477, 847, 37, 240, 620, 586, 493, 645, 11, 40, 882, 665, 222, 593, 724, 555, 928, 527, 931, 809, 938, 498, 69, 343, 860, 983, 329, 291, 650, 659, 324, 654, 70, 802, 489, 724, 811, 390, 90, 800, 261, 683, 142, 198, 831, 913, 543, 312, 384, 200, 282, 632, 928, 613, 77, 553, 430, 616, 382, 258, 131, 774, 207, 54, 730, 328, 932, 793, 312, 849, 607, 394, 171, 768, 198, 33, 137, 511, 589, 109, 609, 154, 118, 745, 520, 972, 274, 419],
      [880, 1000, 473, 510, 527]
    ],
    "memory":[
      [332, 820, 354, 695, 245, 351, 136, 2, 712, 202, 800, 65, 834, 849, 676, 364, 979, 287, 852, 235, 140, 776, 45, 29, 807, 633, 807, 177, 753, 981, 516, 671, 497, 510, 764, 593, 565, 318, 743, 526, 593, 489, 717, 431, 377, 144, 716, 553, 725, 175, 996, 317, 766, 348, 936, 732, 732, 931, 499, 923, 442, 878, 484, 186, 885, 477, 325, 554, 787, 261, 390, 12, 280, 635, 323, 452, 485, 589, 152, 691, 500, 413, 117, 276, 566, 338, 932, 69, 427, 294, 812, 453, 134, 901, 89, 399, 702, 106, 270, 749, 405, 314, 589, 127, 527, 252, 547, 828, 30, 921, 330, 318, 921, 844, 147, 479, 313, 711, 507, 629, 373, 373, 361, 135, 816, 544, 97, 399, 868, 985, 808, 438, 883, 63, 928, 913, 999, 354, 589, 366, 534, 237, 658, 726, 647, 28, 335, 554, 374, 797, 631, 427, 879, 386, 33, 25, 404, 163, 57, 226, 243, 973, 940, 294, 759, 489, 915, 280, 82, 400, 297, 618, 811, 401, 809, 131, 342, 298, 311, 672, 757, 530, 721, 142, 601, 292, 498, 851, 567, 812, 934, 337, 818, 591, 276, 37, 445, 512, 387, 642, 942, 22, 577, 60, 245, 902, 498, 584, 642, 178, 58, 472, 30, 310, 190, 892, 941, 535, 898, 41, 557, 854, 530, 455, 718, 884, 127, 97, 451, 618, 52, 931, 696, 545, 387, 512, 526, 908, 309, 587, 556, 335, 289, 304, 885, 69, 949, 705, 193, 615, 696, 142, 2, 609, 258, 818, 110, 11, 597, 311, 452, 440, 233, 904, 23, 667, 799, 763, 583, 907, 783, 675, 918, 112, 204, 951, 926, 421, 416, 45, 401, 67, 297, 367, 7, 76, 986, 983],
      [552, 65, 470, 759, 98, 783, 190, 332, 379, 447, 824, 763, 214, 871, 468, 802, 839, 517, 507, 848, 19, 825, 176, 681, 115, 333, 495, 480, 182, 334, 384, 1000, 870, 168, 752, 987, 309, 572, 640, 351, 716, 579, 901, 58, 343, 341, 186, 130, 728, 809, 705, 72, 694, 315, 167, 88, 245, 635, 400, 408, 942, 953, 363, 123, 205, 701, 634, 829, 560, 903, 340, 647, 130, 576, 862, 805, 637, 77, 542, 384, 214, 831, 723, 535, 829, 827, 210, 143, 927, 386, 366, 540, 552, 690, 556, 565, 435, 76, 7, 838, 73, 890, 55, 737, 124, 464, 143, 426, 897, 818, 233, 86, 166, 868, 412, 439, 781, 842, 456, 658, 882, 870, 24, 392, 527, 813, 573, 837, 37, 477, 552, 785, 712, 48, 731, 574, 733, 452, 32, 864, 545, 892, 812, 148, 700, 397, 663, 938, 972, 56, 30, 45, 796, 755, 706, 785, 575, 433, 196, 41, 504, 972, 155, 459, 266, 608, 955, 625, 438, 566, 511, 687, 714, 974, 80, 166, 956, 577, 143, 752, 14, 674, 920, 790, 429, 521, 688, 35, 184, 222, 635, 20, 554, 853, 542, 210, 228, 456, 421, 875, 477, 847, 37, 240, 620, 586, 493, 645, 11, 40, 882, 665, 222, 593, 724, 555, 928, 527, 931, 809, 938, 498, 69, 343, 860, 983, 329, 291, 650, 659, 324, 654, 70, 802, 489, 724, 811, 390, 90, 800, 261, 683, 142, 198, 831, 913, 543, 312, 384, 200, 282, 632, 928, 613, 77, 553, 430, 616, 382, 258, 131, 774, 207, 54, 730, 328, 932, 793, 312, 849, 607, 394, 171, 768, 198, 33, 137, 511, 589, 109, 609, 154, 118, 745, 520, 972, 274, 419],
      [880, 1000, 473, 510, 527]
    ],
    "disk":[202, 800, 65, 834, 849, 676, 364, 979, 287, 852, 235, 140, 776, 45, 29, 807, 633, 807, 177, 753, 981, 516, 671, 497, 510, 764, 593, 565, 318, 743, 526, 593, 489, 717, 431, 377, 144, 716, 553, 725, 175, 996, 317, 766, 348, 936, 732, 732, 931, 499, 923, 442, 878, 484, 186, 885, 477, 325, 554, 787, 261, 390, 12, 280, 635, 323, 452, 485, 589, 152, 552, 65, 470, 759, 98, 783, 190, 332, 379, 447, 824, 763, 214, 871, 468, 802, 839, 517, 507, 848, 19, 825, 176, 681, 115, 333, 495, 480, 838, 73],
    "nodes":[["深圳", "广州", "厦门", "济南", "沈阳", "重庆", "昆明"],[63, 76, 47, 15, 36, 43, 28]]
  },
}
const files = {
  "wuhan":[["武汉支行1", "武汉支行2", "武汉支行3", "武汉支行4", "武汉支行5", "武汉支行6", "武汉支行7"],[64, 23, 38, 44, 54, 16, 29]],
  "changsha":[["长沙支行1", "长沙支行2", "长沙支行3", "长沙支行4", "长沙支行5", "长沙支行6", "长沙支行7"],[34, 73, 58, 14, 24, 46, 39]],
  "shenzhen":[["深圳支行1", "深圳支行2", "深圳支行3", "深圳支行4", "深圳支行5", "深圳支行6", "深圳支行7"],[64, 23, 38, 44, 54, 16, 29]],
  "guangzhou":[["广州支行1", "广州支行2", "广州支行3", "广州支行4", "广州支行5", "广州支行6", "广州支行7"],[64, 23, 38, 44, 54, 16, 29]]
}
// const maps = {
//   "mnsj":{
//       "上海": [{"name":"徐汇区支行","num":12},{"name":"静安区支行","num":8},{"name":"长宁区支行","num":3}],
//       "广州": [{"name":"白云区支行","num":9},{"name":"天河区支行","num":15},{"name":"海珠区支行","num":7}],
//       "大连": [{"name":"中山区支行","num":10},{"name":"沙河口区支行","num":5}],
//       "拉萨": [{"name":"城关区支行","num":6},{"name":"堆龙德庆区支行","num":2}],
//       "北京": [{"name":"朝阳区支行","num":17},{"name":"海淀区支行","num":11},{"name":"西城区支行","num":12}],
//       "哈尔滨": [{"name":"道里区支行","num":10},{"name":"南岗区支行","num":3},{"name":"松北区支行","num":6}],
//       "武汉": [{"name":"汉阳区支行","num":12},{"name":"武昌区支行","num":7},{"name":"洪山区支行","num":11}]
//   },
//   "shuju":[
//     [ [{ "name": "北京" }, { "name": "上海", "value": 95 }], [{ "name": "北京" }, { "name": "广州", "value": 90 }], [{ "name": "北京" }, { "name": "大连", "value": 80 }], [{ "name": "北京" }, { "name": "南宁", "value": 70 }], [{ "name": "北京" }, { "name": "南昌", "value": 60 }], [{ "name": "北京" }, { "name": "拉萨", "value": 50 }], [{ "name": "北京" }, { "name": "长春", "value": 40 }], [{ "name": "北京" }, { "name": "包头", "value": 30 }], [{ "name": "北京" }, { "name": "重庆", "value": 20 }], [{ "name": "北京" }, { "name": "常州", "value": 10 }], [{ "name": "北京" }, { "name": "三沙", "value": 65 }] ],
//     [ [{ "name": "成都" }, { "name": "武汉", "value": 95 }], [{ "name": "成都" }, { "name": "台北", "value": 90 }], [{ "name": "成都" }, { "name": "哈尔滨", "value": 80 }], [{ "name": "成都" }, { "name": "三亚", "value": 70 }], [{ "name": "成都" }, { "name": "赤峰", "value": 60 }], [{ "name": "成都" }, { "name": "温州", "value": 50 }], [{ "name": "成都" }, { "name": "兰州", "value": 40 }], [{ "name": "成都" }, { "name": "乌鲁木齐", "value": 30 }], [{ "name": "成都" }, { "name": "青岛", "value": 20 }], [{ "name": "成都" }, { "name": "昆明", "value": 10 }], [{ "name": "成都" }, { "name": "长沙", "value": 65 }] ]
//   ]
// }
const maps = {
  "110":{
    error_code:0,
    data:{
      center_name:"北京",
      center_id:11,
      client_city:[
        {
          city_name:"上海",
          value: 95,
          children: [
            {
              name: "徐汇区支行",
              num: 12
            },
            {
              name: "静安区支行",
              num: 8
            },
            {
              name: "长宁区支行",
              num: 3
            },
          ]
        },
        {
          city_name:"广州",
          value: 90
        },
        {
          city_name:"大连",
          value: 80
        },
        {
          city_name:"南宁",
          value: 70
        }
      ],
      storegateways:[
        {
          gateway_id:1,
          gateway_name:"gateway1"
        },
        {
          gateway_id:2,
          gateway_name:"gateway2"
        },
        {
          gateway_id:3,
          gateway_name:"gateway3"
        },
      ]
    }
  },
  "111":{
    error_code:0,
    data:{
      center_name:"成都",
      center_id:12,
      client_city:[
        {
          city_name:"南昌",
          value: 95
        },
        {
          city_name:"常州",
          value: 90
        },
        {
          city_name:"哈尔滨",
          value: 80,
          children: [
            {
              name: "道里区支行",
              num: 10
            },
            {
              name: "南岗区支行",
              num: 3
            },
            {
              name: "松北区支行",
              num: 6
            },
          ]
        },
        {
          city_name:"兰州",
          value: 70
        }
      ],
      storegateways:[
        {
          gateway_id:1,
          gateway_name:"gateway1"
        },
        {
          gateway_id:2,
          gateway_name:"gateway2"
        }
      ]
    }
  }
}

export default {
  lines: config => {
    return lines[config.body]
  },
  files: config => {
    return files[config.body]
  },
  maps: config => {
    return maps[config.body]
  },
  choice: config => {
    return inter;
  }
}